package com.github.rahmnathan.localmovie.web.repository;

import com.github.rahmnathan.localmovie.domain.AndroidPushClient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AndroidPushTokenRepository extends CrudRepository<AndroidPushClient, String> {

}
