package com.educonnect.security.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "tbl_member_roles")
@Setter
@Getter
public class MemberRole {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String roleName;

	@ManyToMany(fetch = FetchType.LAZY, mappedBy = "memberRoles")
	private List<Member> members;

	public MemberRole() {
	}

	public MemberRole(String roleName) {
		this.roleName = roleName;
	}

	public boolean addMember(Member member) {
		if (members == null)
			members = new ArrayList<>();
		return members.add(member);
	}

}
