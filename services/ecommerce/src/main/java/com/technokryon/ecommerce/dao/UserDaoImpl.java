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
import com.technokryon.ecommerce.pojo.USER;
import com.technokryon.ecommerce.pojo.USERSESSION;

@Repository("UserDao")
@Transactional
@Component

public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory O_SessionFactory;

	@Override
	public USER isUserEmailAvailable(String mail) {

		ModelMapper O_ModelMapper = new ModelMapper();

		String getUserByEmail = "FROM TKECMUSER WHERE mail= :email";

		Query getUserByEmailQry = O_SessionFactory.getCurrentSession().createQuery(getUserByEmail);
		getUserByEmailQry.setParameter("email", mail);

		TKECMUSER O_TKECMUSER = (TKECMUSER) getUserByEmailQry.uniqueResult();

		if (O_TKECMUSER == null) {

			return null;

		}

		USER O_PJ_TKECMUSER = O_ModelMapper.map(O_TKECMUSER, USER.class);

//		O_PJ_TKECMUSER.setTkecmuPassword(O_TKECMUSER.getTkecmuPassword());
//		O_PJ_TKECMUSER.setTkecmuId(O_TKECMUSER.getTkecmuId());
//		O_PJ_TKECMUSER.setTkecmuStatus(O_TKECMUSER.getTkecmuStatus());
//		O_PJ_TKECMUSER.setTkecmuOtpStatus(O_TKECMUSER.getTkecmuOtpStatus());

		return O_PJ_TKECMUSER;

	}

	@Override
	public String createNewUserByEmail(USER RO_UserPojo) {
		Session session = O_SessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		String getUserId = "FROM TKECMUSER ORDER BY id DESC";

		Query userIdQuery = O_SessionFactory.getCurrentSession().createQuery(getUserId);
		userIdQuery.setMaxResults(1);
		TKECMUSER O_TKECM_USER1 = (TKECMUSER) userIdQuery.uniqueResult();

		TKECMUSER O_TKECM_USER = new TKECMUSER();

		if (O_TKECM_USER1 == null) {

			O_TKECM_USER.setId("TKECU0001");
		} else {

			String userId = O_TKECM_USER1.getId();
			Integer Ag = Integer.valueOf(userId.substring(5));
			Ag++;

			System.err.println(Ag);
			O_TKECM_USER.setId("TKECU" + String.format("%04d", Ag));
		}

		try {

			O_TKECM_USER.setName(RO_UserPojo.getName());
			O_TKECM_USER.setMail(RO_UserPojo.getMail());
			O_TKECM_USER.setPassword(RO_UserPojo.getPassword());
			O_TKECM_USER.setCreatedDate(OffsetDateTime.now());
			O_TKECM_USER.setRegType("E");
			O_TKECM_USER.setStatus("N");
			O_TKECM_USER.setOtpStatus("N");
			session.save(O_TKECM_USER);
			tx.commit();
			session.close();

			System.out.println("New User Created By EmailId");
			return O_TKECM_USER.getId();

		} catch (Exception e) {
			e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();
			}
			session.close();

			return null;

		}
	}

	@Override
	public String saveOTPDetails(Integer oTP, String userId) {
		Session session = O_SessionFactory.getCurrentSession();

		String hash = new BCryptPasswordEncoder().encode(OffsetDateTime.now().toString());

		TKECMUSER O_TKECMUSER = session.get(TKECMUSER.class, userId);

		O_TKECMUSER.setOtp(oTP);
		O_TKECMUSER.setHashKey(hash);
		O_TKECMUSER.setOtpExp((OffsetDateTime.now().plusMinutes(5)));

		session.save(O_TKECMUSER);

		return hash;
	}

	@Override
	public USER isUserPhoneNoAvailable(BigInteger phoneNo) {

		ModelMapper O_ModelMapper = new ModelMapper();

		String getUserByPhone = "FROM TKECMUSER  WHERE phone= :phoneNo";

		Query getUserByPhoneQry = O_SessionFactory.getCurrentSession().createQuery(getUserByPhone);
		getUserByPhoneQry.setParameter("phoneNo", phoneNo);

		TKECMUSER O_TKECMUSER = (TKECMUSER) getUserByPhoneQry.uniqueResult();

		if (O_TKECMUSER == null) {

			return null;

		}

		USER O_USER = O_ModelMapper.map(O_TKECMUSER, USER.class);

//		O_PJ_TKECMUSER.setTkecmuPassword(O_TKECMUSER.getTkecmuPassword());
//		O_PJ_TKECMUSER.setTkecmuId(O_TKECMUSER.getTkecmuId());
//		O_PJ_TKECMUSER.setTkecmuStatus(O_TKECMUSER.getTkecmuStatus());
//		O_PJ_TKECMUSER.setTkecmuOtpStatus(O_TKECMUSER.getTkecmuOtpStatus());

		return O_USER;

	}

	@Override
	public String createNewUserByPhoneNo(USER RO_UserPojo) {
		Session session = O_SessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		String getUserId = "FROM TKECMUSER ORDER BY id DESC";

		Query userIdQuery = O_SessionFactory.getCurrentSession().createQuery(getUserId);
		userIdQuery.setMaxResults(1);
		TKECMUSER O_TKECM_USER1 = (TKECMUSER) userIdQuery.uniqueResult();

		TKECMUSER O_TKECM_USER = new TKECMUSER();

		if (O_TKECM_USER1 == null) {

			O_TKECM_USER.setId("TKECU0001");
		} else {

			String userId = O_TKECM_USER1.getId();
			Integer Ag = Integer.valueOf(userId.substring(5));
			Ag++;

			System.err.println(Ag);
			O_TKECM_USER.setId("TKECU" + String.format("%04d", Ag));
		}

		try {

			O_TKECM_USER.setName(RO_UserPojo.getName());
			O_TKECM_USER.setOtp(RO_UserPojo.getOtp());
			O_TKECM_USER.setPhone(RO_UserPojo.getPhone());
			O_TKECM_USER.setPassword(RO_UserPojo.getPassword());
			O_TKECM_USER.setCreatedDate(OffsetDateTime.now());
			O_TKECM_USER.setRegType("M");
			O_TKECM_USER.setCountryCode(RO_UserPojo.getCountryCode());
			O_TKECM_USER.setOtpStatus("N");
			O_TKECM_USER.setStatus("N");
			session.save(O_TKECM_USER);
			tx.commit();
			session.close();

			System.out.println("New User Created By PhoneNo");
			return O_TKECM_USER.getId();

		} catch (Exception e) {
			e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();
			}
			session.close();

			return null;

		}
	}

	@Override
	public USER getUserDetailHash(USER RO_UserPojo) {

		String getUserDetail = "FROM TKECMUSER WHERE hashKey =:hashKey";

		ModelMapper O_ModelMapper = new ModelMapper();

		Query query = O_SessionFactory.getCurrentSession().createQuery(getUserDetail);
		query.setParameter("hashKey", RO_UserPojo.getHashKey());
		TKECMUSER O_TKECMUSER = (TKECMUSER) query.uniqueResult();

		if (O_TKECMUSER == null) {
			return null;
		}
		USER O_USER = O_ModelMapper.map(O_TKECMUSER, USER.class);

//		O_PJ_TKECMUSER.setTkecmuOtp(O_TKECMUSER.getTkecmuOtp());
//		O_PJ_TKECMUSER.setTkecmuOtpExp(O_TKECMUSER.getTkecmuOtpExp());
//		O_PJ_TKECMUSER.setTkecmuId(O_TKECMUSER.getTkecmuId());

		return O_USER;
	}

	@Override
	public void changeOTPStatus(String userId) {

		TKECMUSER O_TKECMUSER = O_SessionFactory.getCurrentSession().get(TKECMUSER.class, userId);
		O_TKECMUSER.setStatus("Y");
		O_TKECMUSER.setOtpStatus("Y");
		O_SessionFactory.getCurrentSession().update(O_TKECMUSER);
	}

	@Override
	public void updatePassword(USER o_USER_DETAIL) {

		TKECMUSER O_TKECMUSER = O_SessionFactory.getCurrentSession().get(TKECMUSER.class,
				o_USER_DETAIL.getId());
		O_TKECMUSER.setPassword(o_USER_DETAIL.getPassword());
		O_TKECMUSER.setModifideDate(OffsetDateTime.now());
		O_SessionFactory.getCurrentSession().update(O_TKECMUSER);

	}

	@Override
	public USERSESSION getApiSecretDataByNewSecret(String apisecret, String userId) {

		TKECMUSER O_TKECMUSER = O_SessionFactory.getCurrentSession().get(TKECMUSER.class, userId);

		Session session = O_SessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		TKECTUSERSESSION O_TKECTUSERSESSION = new TKECTUSERSESSION();
		O_TKECTUSERSESSION.setUserId(O_TKECMUSER);
		O_TKECTUSERSESSION.setApiKey(apisecret);
		O_TKECTUSERSESSION.setCreatedDate(OffsetDateTime.now());
		O_TKECTUSERSESSION.setAliveYN("Y");
		session.save(O_TKECTUSERSESSION);

		tx.commit();
		session.close();

		USERSESSION O_USERSESSION = new USERSESSION();

		O_USERSESSION.setApiKey(O_TKECTUSERSESSION.getApiKey());

		return O_USERSESSION;
	}

	@Override
	public Boolean addAuditDetail(USER O_USER_DETAIL, HttpServletRequest httpServletRequest) {

		TKECTUSERAUDIT O_TKECTUSERAUDIT = new TKECTUSERAUDIT();

		O_TKECTUSERAUDIT.setLoginTime(OffsetDateTime.now());
		O_TKECTUSERAUDIT.setUserAgent(httpServletRequest.getHeader("user-agent"));
		O_TKECTUSERAUDIT.setUserId(O_USER_DETAIL.getId());
		O_TKECTUSERAUDIT.setApiKey(O_USER_DETAIL.getApiKey());
		// O_TKECTUSERAUDIT.setTKECTUA_IP(httpServletRequest.getRemoteHost());

		O_SessionFactory.getCurrentSession().save(O_TKECTUSERAUDIT);

		return true;
	}

	@Override
	public USER getUserDetailAPIKey(String apiKey) {

		ModelMapper O_ModelMapper = new ModelMapper();
		String apiQuery = "FROM TKECTUSERSESSION WHERE apiKey =:apikey";

		Query query = O_SessionFactory.getCurrentSession().createQuery(apiQuery);
		query.setParameter("apikey", apiKey);
		TKECTUSERSESSION O_TKECTUSERSESSION = (TKECTUSERSESSION) query.uniqueResult();

		if (O_TKECTUSERSESSION == null) {
			return null;
		}
		// TKECMUSER O_TKECMUSER = O_TKECTUSERSESSION.getTkectusUserId();
		// PJ_TKECMUSER O_PJ_TKECMUSER = O_ModelMapper.map(O_TKECMUSER,
		// PJ_TKECMUSER.class);

		USER O_USER = new USER();

		O_USER.setName(O_TKECTUSERSESSION.getUserId().getName());
		O_USER.setMail(O_TKECTUSERSESSION.getUserId().getMail());
		O_USER.setPhone(O_TKECTUSERSESSION.getUserId().getPhone());
		O_USER.setPassword(O_TKECTUSERSESSION.getUserId().getPassword());
		O_USER.setId(O_TKECTUSERSESSION.getUserId().getId());
		return O_USER;
	}

	@Override
	public Boolean userLogout(String apiKey) {
		Session session = O_SessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		String getApiKey = "FROM TKECTUSERSESSION WHERE apiKey =:apiKey";

		try {

			Query getApiKeyQuery = session.createQuery(getApiKey);
			getApiKeyQuery.setParameter("apiKey", apiKey);
			TKECTUSERSESSION O_TKECTUSERSESSION = (TKECTUSERSESSION) getApiKeyQuery.uniqueResult();

			O_TKECTUSERSESSION.setAliveYN("N");

			session.update(O_TKECTUSERSESSION);

			String getUserId1 = "FROM TKECTUSERAUDIT WHERE apiKey =:apikey ";

			Query query1 = session.createQuery(getUserId1);
			query1.setParameter("apikey", apiKey);

			TKECTUSERAUDIT O_TKECTUSERAUDIT = (TKECTUSERAUDIT) query1.uniqueResult();
			O_TKECTUSERAUDIT.setLogouttime((OffsetDateTime.now()));
			session.update(O_TKECTUSERAUDIT);

			tx.commit();
			session.close();

		}

		catch (Exception e) {

			e.printStackTrace();
			if (tx.isActive()) {
				tx.rollback();
				session.close();
			}
			return false;
		}
		return true;
	}
}
