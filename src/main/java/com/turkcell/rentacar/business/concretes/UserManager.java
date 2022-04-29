package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.UserService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.dtos.user.GetUserDto;
import com.turkcell.rentacar.business.dtos.user.UserListDto;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.dataAccess.abstracts.UserDao;
import com.turkcell.rentacar.entities.concretes.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserManager implements UserService {

    private final UserDao<User> userDao;
    private final ModelMapperService modelMapperService;

    @Override
    public DataResult<List<UserListDto>> getAll() {

        List<User> result = this.userDao.findAll();

        List<UserListDto> response = result.stream().map(user -> this.modelMapperService
                .forDto().map(user, UserListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<UserListDto>>(response, BusinessMessages.USERS_LISTED);
    }

    @Override
    public DataResult<GetUserDto> getByUserId(Integer id) throws BusinessException {

        checkIfUserIdExists(id);

        User user = this.userDao.getById(id);

        GetUserDto response = this.modelMapperService.forDto().map(user, GetUserDto.class);

        return new SuccessDataResult<>(response, BusinessMessages.USER_FOUND_BY_ID);
    }

    @Override
    public void checkIfUserIdExists(Integer id) throws BusinessException {

        this.userDao.findById(id).orElseThrow(() -> new BusinessException("User not exist with id: " + id));

    }

}
