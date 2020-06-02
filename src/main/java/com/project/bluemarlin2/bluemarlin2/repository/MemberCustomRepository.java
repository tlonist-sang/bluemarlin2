package com.project.bluemarlin2.bluemarlin2.repository;

import com.project.bluemarlin2.bluemarlin2.domain.Member;

import java.util.List;

public interface MemberCustomRepository {
    Member findByUserIdAndFetchUrlSources(String userId);
    Member encodeAndSave(Member member);
    List<Member> getAllMembers();
}
