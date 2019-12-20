package com.technokryon.ecommerce.dao;

import java.math.BigInteger;
import java.time.OffsetDateTime;

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

	@Autowired
	private ModelMapper O_ModelMapper;
	
	@Override
	public USER isUserEmailAvailable(String mail) {

		String getUserByEmail = "FROM TKECMUSER WHERE uMail= :email";

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

		String getUserId = "FROM TKECMUSER ORDER BY uId DESC";

		Query userIdQuery = O_SessionFactory.getCurrentSession().createQuery(getUserId);
		userIdQuery.setMaxResults(1);
		TKECMUSER O_TKECM_USER1 = (TKECMUSER) userIdQuery.uniqueResult();

		TKECMUSER O_TKECM_USER = new TKECMUSER();

		if (O_TKECM_USER1 == null) {

			O_TKECM_USER.setUId("TKECU0001");
		} else {

			String userId = O_TKECM_USER1.getUId();
			Integer Ag = Integer.valueOf(userId.substring(5));
			Ag++;

			System.err.println(Ag);
			O_TKECM_USER.setUId("TKECU" + String.format("%04d", Ag));
		}

		try {

			O_TKECM_USER.setUName(RO_UserPojo.getUName());
			O_TKECM_USER.setUMail(RO_UserPojo.getUMail());
			O_TKECM_USER.setUPassword(RO_UserPojo.getUPassword());
			O_TKECM_USER.setUCreatedDate(OffsetDateTime.now());
			O_TKECM_USER.setURegType("E");
			O_TKECM_USER.setUStatus("N");
			O_TKECM_USER.setUOtpStatus("N");
			session.save(O_TKECM_USER);
			tx.commit();
			session.close();

			System.out.println("New User Created By EmailId");
			return O_TKECM_USER.getUId();

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

		O_TKECMUSER.setUOtp(oTP);
		O_TKECMUSER.setUHashKey(hash);
		O_TKECMUSER.setUOtpExp((OffsetDateTime.now().plusMinutes(5)));

		session.save(O_TKECMUSER);

		return hash;
	}

	@Override
	public USER isUserPhoneNoAvailable(BigInteger phoneNo) {

		String getUserByPhone = "FROM TKECMUSER  WHERE uPhone= :phoneNo";

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

		String getUserId = "FROM TKECMUSER ORDER BY uId DESC";

		Query userIdQuery = O_SessionFactory.getCurrentSession().createQuery(getUserId);
		userIdQuery.setMaxResults(1);
		TKECMUSER O_TKECM_USER1 = (TKECMUSER) userIdQuery.uniqueResult();

		TKECMUSER O_TKECM_USER = new TKECMUSER();

		if (O_TKECM_USER1 == null) {

			O_TKECM_USER.setUId("TKECU0001");
		} else {

			String userId = O_TKECM_USER1.getUId();
			Integer Ag = Integer.valueOf(userId.substring(5));
			Ag++;

			System.err.println(Ag);
			O_TKECM_USER.setUId("TKECU" + String.format("%04d", Ag));
		}

		try {

			O_TKECM_USER.setUName(RO_UserPojo.getUName());
			O_TKECM_USER.setUOtp(RO_UserPojo.getUOtp());
			O_TKECM_USER.setUPhone(RO_UserPojo.getUPhone());
			O_TKECM_USER.setUPassword(RO_UserPojo.getUPassword());
			O_TKECM_USER.setUCreatedDate(OffsetDateTime.now());
			O_TKECM_USER.setURegType("M");
			O_TKECM_USER.setUCountryCode(RO_UserPojo.getUCountryCode());
			O_TKECM_USER.setUOtpStatus("N");
			O_TKECM_USER.setUStatus("N");
			session.save(O_TKECM_USER);
			tx.commit();
			session.close();

			System.out.println("New User Created By PhoneNo");
			return O_TKECM_USER.getUId();

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

		String getUserDetail = "FROM TKECMUSER WHERE uHashKey =:hashKey";

		Query query = O_SessionFactory.getCurrentSession().createQuery(getUserDetail);
		query.setParameter("hashKey", RO_UserPojo.getUHashKey());
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
		O_TKECMUSER.setUStatus("Y");
		O_TKECMUSER.setUOtpStatus("Y");
		O_SessionFactory.getCurrentSession().update(O_TKECMUSER);
	}

	@Override
	public void updatePassword(USER o_USER_DETAIL) {

		TKECMUSER O_TKECMUSER = O_SessionFactory.getCurrentSession().get(TKECMUSER.class, o_USER_DETAIL.getUId());
		O_TKECMUSER.setUPassword(o_USER_DETAIL.getUPassword());
		O_TKECMUSER.setUModifideDate(OffsetDateTime.now());
		O_SessionFactory.getCurrentSession().update(O_TKECMUSER);

	}

	@Override
	public USERSESSION getApiSecretDataByNewSecret(String apisecret, String userId) {

		TKECMUSER O_TKECMUSER = O_SessionFactory.getCurrentSession().get(TKECMUSER.class, userId);

		Session session = O_SessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		TKECTUSERSESSION O_TKECTUSERSESSION = new TKECTUSERSESSION();
		O_TKECTUSERSESSION.setUsUserId(O_TKECMUSER);
		O_TKECTUSERSESSION.setUsApiKey(apisecret);
		O_TKECTUSERSESSION.setUsCreatedDate(OffsetDateTime.now());
		O_TKECTUSERSESSION.setUsAliveYN("Y");
		session.save(O_TKECTUSERSESSION);

		tx.commit();
		session.close();

		USERSESSION O_USERSESSION = new USERSESSION();

		O_USERSESSION.setUsApiKey(O_TKECTUSERSESSION.getUsApiKey());

		return O_USERSESSION;
	}

	@Override
	public Boolean addAuditDetail(USER O_USER_DETAIL, HttpServletRequest httpServletRequest) {

		TKECTUSERAUDIT O_TKECTUSERAUDIT = new TKECTUSERAUDIT();

		O_TKECTUSERAUDIT.setUaLoginTime(OffsetDateTime.now());
		O_TKECTUSERAUDIT.setUaUserAgent(httpServletRequest.getHeader("user-agent"));
		O_TKECTUSERAUDIT.setUaUserId(O_USER_DETAIL.getUId());
		O_TKECTUSERAUDIT.setUaApiKey(O_USER_DETAIL.getApiKey());
		// O_TKECTUSERAUDIT.setTKECTUA_IP(httpServletRequest.getRemoteHost());

		O_SessionFactory.getCurrentSession().save(O_TKECTUSERAUDIT);

		return true;
	}

	@Override
	public USER getUserDetailAPIKey(String apiKey) {

		ModelMapper O_ModelMapper = new ModelMapper();
		String apiQuery = "FROM TKECTUSERSESSION WHERE usApiKey =:apikey";

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

		O_USER.setUName(O_TKECTUSERSESSION.getUsUserId().getUName());
		O_USER.setUMail(O_TKECTUSERSESSION.getUsUserId().getUMail());
		O_USER.setUPhone(O_TKECTUSERSESSION.getUsUserId().getUPhone());
		O_USER.setUPassword(O_TKECTUSERSESSION.getUsUserId().getUPassword());
		O_USER.setUId(O_TKECTUSERSESSION.getUsUserId().getUId());
		return O_USER;
	}

	@Override
	public Boolean userLogout(String apiKey) {
		Session session = O_SessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		String getApiKey = "FROM TKECTUSERSESSION WHERE usApiKey =:apiKey";

		try {

			Query getApiKeyQuery = session.createQuery(getApiKey);
			getApiKeyQuery.setParameter("apiKey", apiKey);
			TKECTUSERSESSION O_TKECTUSERSESSION = (TKECTUSERSESSION) getApiKeyQuery.uniqueResult();

			O_TKECTUSERSESSION.setUsAliveYN("N");

			session.update(O_TKECTUSERSESSION);

			String getUserId1 = "FROM TKECTUSERAUDIT WHERE uaApiKey =:apikey ";

			Query query1 = session.createQuery(getUserId1);
			query1.setParameter("apikey", apiKey);

			TKECTUSERAUDIT O_TKECTUSERAUDIT = (TKECTUSERAUDIT) query1.uniqueResult();
			O_TKECTUSERAUDIT.setUaLogoutTime((OffsetDateTime.now()));
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
