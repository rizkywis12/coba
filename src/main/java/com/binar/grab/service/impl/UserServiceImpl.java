package com.binar.grab.service.impl;

import com.binar.grab.config.Config;
import com.binar.grab.dao.request.RegisterModel;
import com.binar.grab.model.oauth.Role;
import com.binar.grab.model.oauth.User;
import com.binar.grab.repository.oauth.RoleRepository;
import com.binar.grab.repository.oauth.UserRepository;
import com.binar.grab.service.UserService;
import com.binar.grab.util.TemplateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
@Service
public class UserServiceImpl implements UserService {

    Config config = new Config();
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    RoleRepository repoRole;

    @Autowired
    UserRepository repoUser;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    public TemplateResponse templateResponse;
    @Override
    public Map registerManual(RegisterModel objModel) {
        Map map = new HashMap();
        try {
            String[] roleNames = {"ROLE_USER", "ROLE_READ", "ROLE_WRITE"}; // admin
            User user = new User();
            user.setUsername(objModel.getEmail().toLowerCase());
            user.setFullname(objModel.getFullname());

            //step 1 : perlu aktifkan,
            user.setEnabled(false); // matikan user

            String password = encoder.encode(objModel.getPassword().replaceAll("\\s+", ""));
            List<Role> r = repoRole.findByNameIn(roleNames);

            user.setRoles(r);
            user.setPassword(password);
            User obj = repoUser.save(user);

            return templateResponse.templateSukses(obj);

        } catch (Exception e) {
            logger.error("Eror registerManual=", e);
            return templateResponse.templateEror("eror:"+e);
        }

    }
}
