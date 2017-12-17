package com.educonnect.security.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

@Entity(name = "tbl_members")
@Getter
@Setter
public class Member {

	@Id
	@GeneratedValue
	private Long id;

	@Column(nullable = false, unique = true, length = 50)
	private String uid;

	@Column(length = 200)
	private String upw;

	private String alias;

	private String provider;

	@CreationTimestamp
	private Date regdate;
	@UpdateTimestamp
	private Date updatedate;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name ="id")
	private List<MemberRole> memberRoles;

	public Member() {
	}

	
	public boolean matchPassword(String upw) {
		return this.upw.equals(upw);
	}

	public void setProvider(SecuritySocialProvider provider) {
		this.provider = provider.getProviderId();

	}

	public boolean addMemberRoles(MemberRole memberRole) {
		if (memberRoles == null)
			memberRoles = new ArrayList<>();

		return memberRoles.add(memberRole);
	}
}
