package com.example.demo.repository;

import com.example.demo.model.tables.records._UserRecord;
import com.example.demo.user.User;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import static com.example.demo.model.Tables._USER;

@RequiredArgsConstructor
@Transactional
@Repository
public class UserRepository implements JOOQRepository<User> {

    private final DSLContext context;

    @Override
    public Optional<User> findByEmail(String email) {
        User user = context.selectFrom(_USER).where(_USER.EMAIL.eq(email)).fetchOneInto(User.class);
        return (ObjectUtils.isEmpty(user)) ? Optional.empty() : Optional.of(user);
    }

    @Override
    public User save(User user) {
        _UserRecord userRecord = context.insertInto(_USER)
                .set(_USER.FIRSTNAME, user.getFirstname())
                .set(_USER.LASTNAME, user.getLastname())
                .set(_USER.PASSWORD, user.getPassword())
                .set(_USER.ROLE, user.getRole().name())
                .set(_USER.EMAIL, user.getEmail())
                .returning(_USER.ID).fetchOne();

        if (userRecord != null) {
            user.setId(userRecord.getId());
            return user;
        }
        return null;
    }

    public User update(Integer id, User user) {
        _UserRecord userRecord = context.update(_USER)
                .set(_USER.FIRSTNAME, user.getFirstname())
                .set(_USER.LASTNAME, user.getLastname())
                .set(_USER.PASSWORD, user.getPassword())
                .set(_USER.ROLE, user.getRole().name())
                .set(_USER.EMAIL, user.getEmail())
                .where(_USER.ID.eq(id))
                .returning(_USER.ID).fetchOne();
        return ObjectUtils.isEmpty(userRecord) ? null : user;
    }

    @Override
    public Optional<User> findById(Integer id) {
        User user = context.selectFrom(_USER).where(_USER.ID.eq(id)).fetchOneInto(User.class);
        return (ObjectUtils.isEmpty(user)) ? Optional.empty() : Optional.of(user);
    }

    @Override
    public boolean deleteById(Integer id) {
        return context.delete(_USER)
                .where(_USER.ID.eq(id))
                .execute() == 1;
    }

    @Override
    public List<User> findAll() {
        return context.selectFrom(_USER).fetchInto(User.class);
    }
}
