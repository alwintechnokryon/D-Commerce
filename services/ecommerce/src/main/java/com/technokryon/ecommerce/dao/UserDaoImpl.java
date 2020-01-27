package com.technokryon.ecommerce.dao;

import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.model.TKECMUSER;
import com.technokryon.ecommerce.model.TKECTUSERAUDIT;
import com.technokryon.ecommerce.model.TKECTUSERSESSION;
import com.technokryon.ecommerce.pojo.User;
import com.technokryon.ecommerce.pojo.UserSession;

@Repository("UserDao")
@Transactional
@Component

public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public User isUserEmailAvailable(String mail) {

		String getUserByEmail = "FROM TKECMUSER WHERE uMail =:email";

		Query getUserByEmailQry = sessionFactory.getCurrentSession().createQuery(getUserByEmail);
		getUserByEmailQry.setParameter("email", mail);

		TKECMUSER tKECMUSER = (TKECMUSER) getUserByEmailQry.uniqueResult();

		if (tKECMUSER == null) {

			return null;

		}

		User user = modelMapper.map(tKECMUSER, User.class);

//		O_PJ_TKECMUSER.setTkecmuPassword(O_TKECMUSER.getTkecmuPassword());
//		O_PJ_TKECMUSER.setTkecmuId(O_TKECMUSER.getTkecmuId());
//		O_PJ_TKECMUSER.setTkecmuStatus(O_TKECMUSER.getTkecmuStatus());
//		O_PJ_TKECMUSER.setTkecmuOtpStatus(O_TKECMUSER.getTkecmuOtpStatus());

		return user;

	}

	@Override
	public String createNewUserByEmail(User user) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		String getUserId = "FROM TKECMUSER ORDER BY uId DESC";

		Query userIdQuery = sessionFactory.getCurrentSession().createQuery(getUserId);
		userIdQuery.setMaxResults(1);
		TKECMUSER tKECMUSER1 = (TKECMUSER) userIdQuery.uniqueResult();

		TKECMUSER tKECMUSER = new TKECMUSER();

		if (tKECMUSER1 == null) {

			tKECMUSER.setUId("TKECU0001");
		} else {

			String userId = tKECMUSER1.getUId();
			Integer Ag = Integer.valueOf(userId.substring(5));
			Ag++;

			// System.err.println(Ag);
			tKECMUSER.setUId("TKECU" + String.format("%04d", Ag));
		}

		try {

			tKECMUSER.setUName(user.getUName());
			tKECMUSER.setUMail(user.getUMail());
			tKECMUSER.setUPassword(user.getUPassword());
			tKECMUSER.setUCreatedDate(OffsetDateTime.now());
			tKECMUSER.setURegType("E");
			tKECMUSER.setUStatus("N");
			tKECMUSER.setUOtpStatus("N");
			session.save(tKECMUSER);
			transaction.commit();
			session.close();

			System.out.println("New User Created By EmailId");
			return tKECMUSER.getUId();

		} catch (Exception e) {
			e.printStackTrace();
			if (transaction.isActive()) {
				transaction.rollback();
			}
			session.close();

			return null;

		}
	}

	@Override
	public String saveOTPDetails(Integer oTP, String userId) {

		String hash = new BCryptPasswordEncoder().encode(OffsetDateTime.now().toString());

		TKECMUSER tKECMUSER = sessionFactory.getCurrentSession().get(TKECMUSER.class, userId);

		tKECMUSER.setUOtp(oTP);
		tKECMUSER.setUHashKey(hash);
		tKECMUSER.setUOtpExp((OffsetDateTime.now().plusMinutes(5)));

		sessionFactory.getCurrentSession().save(tKECMUSER);

		return hash;
	}

	@Override
	public User isUserPhoneNoAvailable(BigInteger phoneNo) {

		String getUserByPhone = "FROM TKECMUSER WHERE uPhone =:phoneNo";

		Query getUserByPhoneQry = sessionFactory.getCurrentSession().createQuery(getUserByPhone);
		getUserByPhoneQry.setParameter("phoneNo", phoneNo);

		TKECMUSER tKECMUSER = (TKECMUSER) getUserByPhoneQry.uniqueResult();

		if (tKECMUSER == null) {

			return null;

		}

		User user = modelMapper.map(tKECMUSER, User.class);

//		O_PJ_TKECMUSER.setTkecmuPassword(O_TKECMUSER.getTkecmuPassword());
//		O_PJ_TKECMUSER.setTkecmuId(O_TKECMUSER.getTkecmuId());
//		O_PJ_TKECMUSER.setTkecmuStatus(O_TKECMUSER.getTkecmuStatus());
//		O_PJ_TKECMUSER.setTkecmuOtpStatus(O_TKECMUSER.getTkecmuOtpStatus());

		return user;

	}

	@Override
	public String createNewUserByPhoneNo(User user) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		String getUserId = "FROM TKECMUSER ORDER BY uId DESC";

		Query userIdQuery = sessionFactory.getCurrentSession().createQuery(getUserId);
		userIdQuery.setMaxResults(1);
		TKECMUSER tKECMUSER1 = (TKECMUSER) userIdQuery.uniqueResult();

		TKECMUSER tKECMUSER = new TKECMUSER();

		if (tKECMUSER1 == null) {

			tKECMUSER.setUId("TKECU0001");
		} else {

			String userId = tKECMUSER1.getUId();
			Integer Ag = Integer.valueOf(userId.substring(5));
			Ag++;

			// System.err.println(Ag);
			tKECMUSER.setUId("TKECU" + String.format("%04d", Ag));
		}

		try {

			tKECMUSER.setUName(user.getUName());
			tKECMUSER.setUOtp(user.getUOtp());
			tKECMUSER.setUPhone(user.getUPhone());
			tKECMUSER.setUPassword(user.getUPassword());
			tKECMUSER.setUCreatedDate(OffsetDateTime.now());
			tKECMUSER.setURegType("M");
			tKECMUSER.setUPhoneCode(user.getUPhoneCode());
			tKECMUSER.setUOtpStatus("N");
			tKECMUSER.setUStatus("N");
			session.save(tKECMUSER);
			transaction.commit();
			session.close();

			System.out.println("New User Created By PhoneNo");
			return tKECMUSER.getUId();

		} catch (Exception e) {
			e.printStackTrace();
			if (transaction.isActive()) {
				transaction.rollback();
			}
			session.close();

			return null;

		}
	}

	@Override
	public User getUserDetailHash(User user) {

		String getUserDetail = "FROM TKECMUSER WHERE uHashKey =:hashKey";

		Query query = sessionFactory.getCurrentSession().createQuery(getUserDetail);
		query.setParameter("hashKey", user.getUHashKey());
		TKECMUSER tKECMUSER = (TKECMUSER) query.uniqueResult();

		if (tKECMUSER == null) {
			return null;
		}
		User user1 = modelMapper.map(tKECMUSER, User.class);

//		O_PJ_TKECMUSER.setTkecmuOtp(O_TKECMUSER.getTkecmuOtp());
//		O_PJ_TKECMUSER.setTkecmuOtpExp(O_TKECMUSER.getTkecmuOtpExp());
//		O_PJ_TKECMUSER.setTkecmuId(O_TKECMUSER.getTkecmuId());

		return user1;
	}

	@Override
	public void changeOTPStatus(String userId) {

		TKECMUSER tKECMUSER = sessionFactory.getCurrentSession().get(TKECMUSER.class, userId);
		tKECMUSER.setUStatus("Y");
		tKECMUSER.setUOtpStatus("Y");
		sessionFactory.getCurrentSession().update(tKECMUSER);
	}

	@Override
	public void updatePassword(User userDetail) {

		TKECMUSER tKECMUSER = sessionFactory.getCurrentSession().get(TKECMUSER.class, userDetail.getUId());
		tKECMUSER.setUPassword(userDetail.getUPassword());
		tKECMUSER.setUModifideDate(OffsetDateTime.now());
		sessionFactory.getCurrentSession().update(tKECMUSER);

	}

	@Override
	public UserSession getApiSecretDataByNewSecret(String apisecret, String userId) {

		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		String sessionUserId = "FROM TKECTUSERSESSION WHERE usTkecmuId.uId =:userId AND usAliveYN =:aliveYN";

		Query sessionUserIdQuery = session.createQuery(sessionUserId);
		sessionUserIdQuery.setParameter("userId", userId);
		sessionUserIdQuery.setParameter("aliveYN", "Y");

		try {
			List<TKECTUSERSESSION> tKECTUSERSESSION1 = sessionUserIdQuery.getResultList();

			for (TKECTUSERSESSION tKECTUSERSESSION2 : tKECTUSERSESSION1) {

				tKECTUSERSESSION2.setUsAliveYN("N");
				session.update(tKECTUSERSESSION2);

			}

			TKECTUSERSESSION tKECTUSERSESSION = new TKECTUSERSESSION();
			tKECTUSERSESSION.setUsTkecmuId(sessionFactory.getCurrentSession().get(TKECMUSER.class, userId));
			tKECTUSERSESSION.setUsApiKey(apisecret);
			tKECTUSERSESSION.setUsCreatedDate(OffsetDateTime.now());
			tKECTUSERSESSION.setUsAliveYN("Y");
			session.save(tKECTUSERSESSION);

			transaction.commit();
			session.close();

			UserSession userSession = new UserSession();

			userSession.setUsApiKey(tKECTUSERSESSION.getUsApiKey());

			return userSession;

		} catch (Exception e) {
			e.printStackTrace();
			if (transaction.isActive()) {
				transaction.rollback();
			}
			session.close();

			return null;
		}
	}

	@Override
	public void addAuditDetail(User userDetail, HttpServletRequest httpServletRequest) {

		TKECTUSERAUDIT tKECTUSERAUDIT = new TKECTUSERAUDIT();

		tKECTUSERAUDIT.setUaLoginTime(OffsetDateTime.now());
		tKECTUSERAUDIT.setUaUserAgent(httpServletRequest.getHeader("user-agent"));
		tKECTUSERAUDIT.setUaUserId(userDetail.getUId());
		tKECTUSERAUDIT.setUaApiKey(userDetail.getApiKey());
		// O_TKECTUSERAUDIT.setTKECTUA_IP(httpServletRequest.getRemoteHost());

		sessionFactory.getCurrentSession().save(tKECTUSERAUDIT);

	}

	@Override
	public User getUserDetailAPIKey(String apiKey) {

		TKECTUSERSESSION tKECTUSERSESSION = sessionFactory.getCurrentSession().get(TKECTUSERSESSION.class, apiKey);

		if (tKECTUSERSESSION == null) {
			return null;
		}
		// TKECMUSER O_TKECMUSER = O_TKECTUSERSESSION.getTkectusUserId();
		// PJ_TKECMUSER O_PJ_TKECMUSER = O_ModelMapper.map(O_TKECMUSER,
		// PJ_TKECMUSER.class);

		User user = new User();

		user.setUName(tKECTUSERSESSION.getUsTkecmuId().getUName());
		user.setUMail(tKECTUSERSESSION.getUsTkecmuId().getUMail());
		user.setUPhone(tKECTUSERSESSION.getUsTkecmuId().getUPhone());
		user.setUPassword(tKECTUSERSESSION.getUsTkecmuId().getUPassword());
		user.setUId(tKECTUSERSESSION.getUsTkecmuId().getUId());
		return user;
	}

	@Override
	public Boolean userLogout(String apiKey) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		try {

			TKECTUSERSESSION tKECTUSERSESSION = session.get(TKECTUSERSESSION.class, apiKey);

			tKECTUSERSESSION.setUsAliveYN("N");

			session.update(tKECTUSERSESSION);

			String getUserId1 = "FROM TKECTUSERAUDIT WHERE uaApiKey =:apikey ";

			Query query1 = session.createQuery(getUserId1);
			query1.setParameter("apikey", apiKey);

			TKECTUSERAUDIT tKECTUSERAUDIT = (TKECTUSERAUDIT) query1.uniqueResult();
			tKECTUSERAUDIT.setUaLogoutTime((OffsetDateTime.now()));
			session.update(tKECTUSERAUDIT);

			transaction.commit();
			session.close();

		}

		catch (Exception e) {

			e.printStackTrace();
			if (transaction.isActive()) {
				transaction.rollback();
				session.close();
			}
			return false;
		}
		return true;
	}
}
