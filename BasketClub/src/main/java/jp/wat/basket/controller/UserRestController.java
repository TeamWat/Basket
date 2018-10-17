package jp.wat.basket.controller;

import java.io.IOException;
import java.util.List;

import jp.wat.basket.entity.Member;
import jp.wat.basket.entity.UserMember;
import jp.wat.basket.entity.UserNendo;
import jp.wat.basket.service.MemberService;
import jp.wat.basket.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class UserRestController {

	@Autowired
	MemberService memberService;
	
	@Autowired
	UserService userService;
	
	/*
	 * メンバー情報を取得する
	 */
	@RequestMapping(value="/user/getPermissionMember", method=RequestMethod.POST)
	@ResponseBody
	public List<Member> getPermissionMember(@RequestBody String params) throws JsonParseException, JsonMappingException, IOException {
		
		System.out.println(params);
		ObjectMapper mapper = new ObjectMapper();
		UserNendo userNendo = mapper.readValue(params, UserNendo.class);
	
		return memberService.getPermissionMember(userNendo.getNendo());
	}

	/*
	 * メンバーの年度情報を登録する
	 */
	@RequestMapping(value="/user/addUserNendo", method=RequestMethod.POST)
	@ResponseBody
	public void addUserNendo(@RequestBody String params) throws JsonParseException, JsonMappingException, IOException {
		
		System.out.println(params);
		ObjectMapper mapper = new ObjectMapper();
		UserNendo userNendo = mapper.readValue(params, UserNendo.class);
	
		userService.addUserNendo(userNendo);
	}
	
	/*
	 * メンバーの年度情報を削除する
	 */
	@RequestMapping(value="/user/removeUserNendo", method=RequestMethod.POST)
	@ResponseBody
	public void removeUserNendo(@RequestBody String params) throws JsonParseException, JsonMappingException, IOException {
		
		System.out.println(params);
		ObjectMapper mapper = new ObjectMapper();
		UserNendo userNendo = mapper.readValue(params, UserNendo.class);
	
		userService.removeUserNendo(userNendo);
	}
	
	/*
	 * ユーザーに紐づくメンバーを取得する
	 */
	@RequestMapping(value="/user/getUserMember", method=RequestMethod.GET)
	@ResponseBody
	public List<Integer> getUserMember(@RequestParam("uid") String uid, @RequestParam("nendo") Integer nendo) throws JsonParseException, JsonMappingException, IOException {
		
		return userService.getUserMember(uid, nendo);
	}
	
	/*
	 * ユーザーに紐づくメンバーを登録する
	 */
	@RequestMapping(value="/user/addUserMember", method=RequestMethod.POST)
	@ResponseBody
	public void addUserMember(@RequestBody String params) throws JsonParseException, JsonMappingException, IOException {
		
		ObjectMapper mapper = new ObjectMapper();
		UserMember userMember = mapper.readValue(params, UserMember.class);
	
		userService.addUserMember(userMember);
	}
	
	/*
	 * ユーザーが閲覧可能なメンバーを削除する
	 */
	@RequestMapping(value="/user/removeUserMember", method=RequestMethod.POST)
	@ResponseBody
	public void removeUsersMember(@RequestBody String params) throws JsonParseException, JsonMappingException, IOException {
		
		System.out.println(params);
		ObjectMapper mapper = new ObjectMapper();
		UserMember userMember = mapper.readValue(params, UserMember.class);
	
		userService.removeUserMember(userMember);
	}	
	
}
