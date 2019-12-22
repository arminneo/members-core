package com.armin.rights.memberscore.stores;

import com.armin.rights.memberscore.models.Member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberStore extends JpaRepository<Member, Long> {

}