package com.minionz.backend.common.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.assertThat;

public class ProfileControllerUnitTest {

    @DisplayName("real profile 조회 테스트")
    @Test
    void getRealProfileTest() {
        // given
        String expectedProfile = "real";
        MockEnvironment env = new MockEnvironment();
        env.addActiveProfile(expectedProfile);
        env.addActiveProfile("real-db");
        ProfileController profileController = new ProfileController(env);
        // when
        String profile = profileController.profile();
        // then
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @DisplayName("real profile 없을 시 첫 번째 조회 성공 테스트")
    @Test
    void getFirstIfNoRealProfileTest() {
        // given
        String expectedProfile = "real-db";
        MockEnvironment env = new MockEnvironment();
        env.addActiveProfile(expectedProfile);
        ProfileController profileController = new ProfileController(env);
        // when
        String profile = profileController.profile();
        // then
        assertThat(profile).isEqualTo(expectedProfile);
    }

    @DisplayName("active profile 없을 시 default 조회 성공 테스트")
    @Test
    void getDefaultIfNoActiveProfile() {
        // given
        String expectedProfile = "default";
        MockEnvironment env = new MockEnvironment();
        ProfileController profileController = new ProfileController(env);
        // when
        String profile = profileController.profile();
        // then
        assertThat(profile).isEqualTo(expectedProfile);
    }
}
