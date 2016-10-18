package com.adbobe.app.service;

import com.adbobe.app.domain.AuthResourceToken;

import java.util.List;

/**
 * Created by venkatamunnangi on 10/12/16.
 */
public interface AuthResourceTokenRepository { //extends MongoRepository<AuthResourceToken, String> {

    AuthResourceToken findByPersonIdAndResourceId(String personId, String resourceId);

    List<AuthResourceToken> findByPersonId(String personId);

    List<AuthResourceToken> deleteByPersonId(String personId);
}
