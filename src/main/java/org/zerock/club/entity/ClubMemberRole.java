package org.zerock.club.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ClubMemberRole {

    USER("USER"), MANAGER("MANAGER"), ADMIN("ADMIN");

    private String name;
}
