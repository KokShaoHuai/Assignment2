package com.kett.TicketSystem.membership.repository;

import com.kett.TicketSystem.membership.domain.Membership;
import com.kett.TicketSystem.membership.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MembershipRepository extends JpaRepository<Membership, UUID> {
    List<Membership> findByUserId(UUID userId);
    List<Membership> findByProjectId(UUID projectId);
    List<Membership> findByProjectIdAndStateEquals(UUID projectId, State state);
    List<Membership> findByUserIdAndStateEquals(UUID userId, State state);

    Boolean existsByUserIdAndProjectId(UUID userId, UUID projectId);

    Long removeById(UUID id);
    void deleteByProjectId(UUID projectId);
}
