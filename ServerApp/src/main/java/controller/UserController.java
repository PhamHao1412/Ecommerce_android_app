package StoreApp.StoreApp.controller;

import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

import StoreApp.StoreApp.entity.User;
import StoreApp.StoreApp.model.Mail;
import StoreApp.StoreApp.service.CloudinaryService;
import StoreApp.StoreApp.service.MailService;
import StoreApp.StoreApp.service.UserService;

@RestController
public class UserController {
	@Autowired
	UserService userService;

	@Autowired
	MailService mailService;

	@Autowired
	HttpSession session;

	@Autowired
	CloudinaryService cloudinaryService;

	@GetMapping(path = "/signin")
	public ResponseEntity<?> login(String id, String password) {
		User user = userService.findByIdAndRole(id, "user");
		if (user != null) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			if (user.getPassword() != null && passwordEncoder.matches(password, user.getPassword())) {
				return new ResponseEntity<>(user, HttpStatus.OK);
			} else {
				return new ResponseEntity<>("Incorrect password", HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<>("User not found", HttpStatus.UNAUTHORIZED);
		}
	}

	@PostMapping(path = "/signup", consumes = "application/x-www-form-urlencoded")
	public ResponseEntity<User> SignUp(String username, String fullname, String email, String password) {
		User user = userService.findByIdAndRole(username, "user");
		if (user == null) {
			String encodedValue = new BCryptPasswordEncoder().encode(password);
			String avatar = "https://haycafe.vn/wp-content/uploads/2022/02/Avatar-trang-den.png";
			User newUser = new User(username, "default", "user", encodedValue, fullname, avatar, email, null, null,null,null);
			userService.saveUser(newUser);
			return new ResponseEntity<>(newUser, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.CONFLICT);
		}
	}
	@PostMapping(path = "/forgot", consumes = "application/x-www-form-urlencoded")
	public ResponseEntity<String> ForgotPassword(String id) {
		User user = userService.findByIdAndRole(id, "user");
		if (user != null) {
			int code = (int) Math.floor(((Math.random() * 899999) + 100000));
			Mail mail = new Mail();
			mail.setMailFrom("phamanhhao1412@gmail.com");
			mail.setMailTo(user.getEmail());
			mail.setMailSubject("For got Password");
			mail.setMailContent("Your code is: " + code);
			mailService.sendEmail(mail);
			session.setAttribute("code", code);
			System.out.println(code);
			return new ResponseEntity<String>(new Gson().toJson(String.valueOf(code)), HttpStatus.OK);
		} else
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping(path = "/forgotnewpass", consumes = "application/x-www-form-urlencoded")
	public ResponseEntity<String> ForgotNewPass(String id, String code, String password) {
		User user = userService.findByIdAndRole(id, "user");
		if (user != null) {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String encodedValue = passwordEncoder.encode(password);
			user.setPassword(encodedValue);
			userService.saveUser(user);
			return new ResponseEntity<String>(password, HttpStatus.OK);
		} else
			return new ResponseEntity<String>(HttpStatus.UNAUTHORIZED);
	}

	@PostMapping(path = "changepassword", consumes = "application/x-www-form-urlencoded")
	public ResponseEntity<String> changePassword(String id, String password, String new_password,String confirm_password) {
		User user = userService.findByIdAndRole(id, "user");
		if(user != null){
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			if (user.getPassword() != null && !passwordEncoder.matches(password, user.getPassword())) {
				return new ResponseEntity<>( "Incorrect password",HttpStatus.NOT_ACCEPTABLE);
			}if (!new_password.equals(confirm_password)) {
				return new ResponseEntity<>("Do not match",HttpStatus.BAD_REQUEST);
			}
		String encodedValue = passwordEncoder.encode(new_password);
		user.setPassword(encodedValue);
		userService.saveUser(user);
		return new ResponseEntity<>(new_password, HttpStatus.OK);
		}
		else
			return new ResponseEntity<>( HttpStatus.UNAUTHORIZED);
	}

	@PostMapping(path = "update", consumes = "multipart/form-data")
	public ResponseEntity<User> UpdateAvatar(String id, MultipartFile avatar, String fullname, String email,
			String phoneNumber, String address) {
		User user = userService.findByIdAndRole(id, "user");
		if (user != null) {
			if (avatar !=null) {
				String url = cloudinaryService.uploadFile(avatar);
				user.setAvatar(url);
			}
			user.setUser_Name(fullname);
			user.setEmail(email);
			user.setPhone_Number(phoneNumber);
			user.setAddress(address);
			userService.saveUser(user);
			if(user.getPassword()!=null)
				user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<User>(HttpStatus.UNAUTHORIZED);
		}
	}
	@PostMapping(path = "google", consumes = "application/x-www-form-urlencoded")
	public ResponseEntity<User> LoginWithGoogle(String id, String fullname, String email, String avatar) {
		User user = userService.findByIdAndRole(id, "user");
		if (user == null) {
			user = userService
					.saveUser(new User(id, "google", "user", null, fullname, avatar, email, null, null, null, null));
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	@GetMapping(path = "/update-user")
	public ResponseEntity<User> updateUser(String id) {
		if (id == null || id.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		User user = userService.findByIdAndRole(id, "user");
		if (user != null) {
			return new ResponseEntity<>(user, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}


}
