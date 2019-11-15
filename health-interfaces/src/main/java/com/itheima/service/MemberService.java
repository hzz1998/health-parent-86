package com.itheima.service;

import com.itheima.pojo.Member;

import java.util.List;

public interface MemberService {
    Member findByTelephone(String telephone);

    void add(Member member);
    List<Member> getFamily(Integer id);
}
