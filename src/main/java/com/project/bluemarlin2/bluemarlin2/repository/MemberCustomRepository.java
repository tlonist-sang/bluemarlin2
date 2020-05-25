package com.project.bluemarlin2.bluemarlin2.repository;

import com.project.bluemarlin2.bluemarlin2.domain.Member;

public interface MemberCustomRepository {
    Member findByUserIdAndFetchUrlSources(String userId);
    Member encodeAndSave(Member member);
}
