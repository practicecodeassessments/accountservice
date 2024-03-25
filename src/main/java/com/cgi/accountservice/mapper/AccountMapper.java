package com.cgi.accountservice.mapper;

import com.cgi.accountservice.data.entity.Account;
import com.cgi.accountservice.model.AccountModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/***
 * This is mapper class from mapstruct framework to copy the value from entity to model class and vice versa
 *
 * @author Rajesh Chanda
 * @version 0.1
 *
 */

@Mapper
public interface AccountMapper
{

  /**
   * create instance of mapper class
   */
  AccountMapper INSTANCE = Mappers.getMapper( AccountMapper.class);

  /**
   * This method copy the value form Customer entity class to Customer model class
   * @param account - create customer model class from input of customer entity input
   * @return customer model class
   */

  AccountModel modelToDto(Account account);

  /**
   * This method copy the value form Customer model class to Customer entity class
   * @param accountModel - create customer entity class from input of customer model input
   * @return customer entity class
   */

  Account DtoToModel(AccountModel accountModel);

}
