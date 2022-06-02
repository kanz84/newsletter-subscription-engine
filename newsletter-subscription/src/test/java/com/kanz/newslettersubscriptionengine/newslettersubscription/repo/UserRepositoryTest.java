package com.kanz.newslettersubscriptionengine.newslettersubscription.repo;

import com.kanz.newslettersubscriptionengine.common.util.JpaUtil;
import com.kanz.newslettersubscriptionengine.newslettersubscription.NewsletterSubscriptionApplication;
import com.kanz.newslettersubscriptionengine.newslettersubscription.entity.UserEntity;
import com.kanz.newslettersubscriptionengine.newslettersubscription.testUtil.TestDataGenerator;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.kanz.newslettersubscriptionengine.newslettersubscription.testUtil.TestDataGenerator.insertMany;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = NewsletterSubscriptionApplication.class)
public class UserRepositoryTest {
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    UserRepository userRepository;

    @After
    public void after() {
        userRepository.deleteAll();
    }

    @Test
    public void createUserTest() {
        UserEntity userEntity = TestDataGenerator.createUserEntity();
        userRepository.save(userEntity);
        Assert.assertNotNull(userEntity.getId());
        Assert.assertTrue(userEntity.getId() > 0);

        Optional<UserEntity> userEntityOptional = userRepository.findById(userEntity.getId());
        UserEntity userEntity3 = userEntityOptional.get();
        Assert.assertNotNull(userEntity3);
        Assert.assertEquals(userEntity.getId(), userEntity3.getId());

    }

    @Test(expected = DataIntegrityViolationException.class)
    public void createUserDuplicatedEmailTest() {
        UserEntity userEntity0 = TestDataGenerator.createUserEntity();
        userRepository.save(userEntity0);
        UserEntity userEntity = TestDataGenerator.createUserEntity();
        userRepository.save(userEntity);
    }

    @Test(expected = TransactionSystemException.class)
    public void createUserInvalidEmailTest() {
        UserEntity userEntity0 = TestDataGenerator.createUserEntity();
        userEntity0.setEmail("a.wrong.email.com");
        userRepository.save(userEntity0);
    }

    @Test
    @Transactional
    public void deleteUserAndExistUserTest() {
        UserEntity userEntity = TestDataGenerator.createUserEntity();
        userRepository.save(userEntity);
        Assert.assertTrue(userEntity.getId() > 0);
        Assert.assertTrue(userRepository.existsByEmail(userEntity.getEmail()));
        userRepository.deleteByEmail(userEntity.getEmail());
        Assert.assertFalse(userRepository.existsByEmail(userEntity.getEmail()));
    }

    @Test
    public void findAllTest() {
        insertMany(userRepository, 7);
        List<UserEntity> userEntities = JpaUtil.findAllPages(userRepository);
        printIt(userEntities);
        Assert.assertEquals(userRepository.count(), userEntities.size());
    }

    @Test
    public void findAllByDateTest() {
        int count = 7;
        Date now = new Date();
        insertMany(userRepository, count);
        PageRequest pageRequest = PageRequest.of(0, 10);
        Date from = DateUtils.addMinutes(now, -15);
        Date to = DateUtils.addMinutes(now, +15);

        assertResult(userRepository.findAllByCreationDateAfter(from, pageRequest), count);
        assertResult(userRepository.findAllByCreationDateBefore(to, pageRequest), count);
        assertResult(userRepository.findAllByCreationDateBetween(from, to , pageRequest), count);
    }

    private void assertResult(Page<UserEntity> userEntitiesPage, int count) {
        List<UserEntity> userEntities = userEntitiesPage.getContent();
        Assert.assertEquals(userEntities.size(), count);
    }

    private void printIt(List<UserEntity> userEntities) {
        userEntities.forEach(userEntity -> logger.info("User entity is : {}", userEntity));
    }

}
