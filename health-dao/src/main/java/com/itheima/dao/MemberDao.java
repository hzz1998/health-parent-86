package com.itheima.dao;

import com.github.pagehelper.Page;
import com.itheima.pojo.Member;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MemberDao {
    public List<Member> findAll();
    public Page<Member> selectByCondition(String queryString);
    public void add(Member member);
    public void deleteById(Integer id);
    public Member findById(Integer id);
    public Member findByTelephone(@Param("telephone") String telephone);
    public void edit(Member member);
    public Integer findMemberCountBeforeDate(String date);
    public Integer findMemberCountByDate(String date);
    public Integer findMemberCountAfterDate(String date);
    public Integer findMemberTotalCount();

    Integer findNancount();
    Integer findNvcount();

    Integer find0018();

    Integer find1830();

    Integer find3045();

    Integer find45();

    List<Member> getMember();

    List<Integer> getMemberByDate(@Param("start1") Date start1,@Param("end1") Date end1);

    Integer queryCountByMonth(String month);
}
