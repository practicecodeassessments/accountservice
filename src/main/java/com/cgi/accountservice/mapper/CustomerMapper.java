package com.cgi.accountservice.mapper;

import com.cgi.accountservice.data.entity.Customer;
import com.cgi.accountservice.model.NewCustomerModel;
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
public interface CustomerMapper {

  /**
   * create instance of mapper class
   */
  CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

  /**
   * This method copy the value form Customer entity class to Customer model class
   * @param customer - create customer model class from input of customer entity input
   * @return customer model class
   */

  NewCustomerModel modelToDto(Customer customer);

  /**
   * This method copy the value form Customer model class to Customer entity class
   * @param customerModel - create customer entity class from input of customer model input
   * @return customer entity class
   */

  Customer DtoToModel(NewCustomerModel customerModel);

}
