package com.technokryon.ecommerce.dao;

import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.model.TKECMUSER;
import com.technokryon.ecommerce.model.TKECTUSERAUDIT;
import com.technokryon.ecommerce.model.TKECTUSERSESSION;
import com.technokryon.ecommerce.pojo.PJ_TKECMUSER;
import com.technokryon.ecommerce.pojo.PJ_TKECTUSERSESSION;

@Repository("UserDao")
@Transactional
@Component

public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory O_SessionFactory;

	@Override
	public PJ_TKECMUSER isUserEmailAvailable(String mail) {

		PJ_TKECMUSER O_PJ_TKECMUSER = new PJ_TKECMUSER();

		String getUserByEmail = "FROM TKECMUSER WHERE TKECMU_MAIL= :email";

		Query getUserByEmailQry = O_SessionFactory.getCurrentSession().createQuery(getUserByEmail);
		getUserByEmailQry.setParameter("email", mail);

		TKECMUSER O_TKECMUSER = (TKECMUSER) getUserByEmailQry.uniqueResult();

		if (O_TKECMUSER == null) {

			return null;

		}

		O_PJ_TKECMUSER.setPassword(O_TKECMUSER.getTKECMU_PASSWORD());
		O_PJ_TKECMUSER.setUserId(O_TKECMUSER.getTKECMU_ID());
		O_PJ_TKECMUSER.setStatus(O_TKECMUSER.getTKECMU_STATUS());
		O_PJ_TKECMUSER.setOtpStatus(O_TKECMUSER.getTKECMU_OTP_STATUS());

		return O_PJ_TKECMUSER;

	}

	@Override
	public String createNewUserByEmail(PJ_TKECMUSER RO_UserPojo) {
		Session session = O_SessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		String getUserId = "FROM TKECMUSER ORDER BY TKECMU_ID DESC";

		Query userIdQuery = O_SessionFactory.getCurrentSession().createQuery(getUserId);
		userIdQuery.setMaxResults(1);
		TKECMUSER O_TKECM_USER1 = (TKECMUSER) userIdQuery.uniqueResult();

		TKECMUSER O_TKECM_USER = new TKECMUSER();

		if (O_TKECM_USER1 == null) {

			O_TKECM_USER.setTKECMU_ID("TKECU0001");
		} else {

			String userId = O_TKECM_USER1.getTKECMU_ID();
			Integer Ag = Integer.valueOf(userId.substring(5));
			Ag++;

			System.err.println(Ag);
			O_TKECM_USER.setTKECMU_ID("TKECU" + String.format("%04d", Ag));
		}

		try {

			O_TKECM_USER.setTKECMU_NAME(RO_UserPojo.getName());
			O_TKECM_USER.setTKECMU_MAIL(RO_UserPojo.getMail());
			O_TKECM_USER.setTKECMU_PASSWORD(RO_UserPojo.getPassword());
			O_TKECM_USER.setTKECMU_CREATED_DATE(OffsetDateTime.now());
			O_TKECM_USER.setTKECMU_REG_TYPE("E");
			O_TKECM_USER.setTKECMU_STATUS("N");
			O_TKECM_USER.setTKECMU_OTP_STATUS("N");
			session.save(O_TKECM_USER);
			tx.commit();
			session.close();

			System.out.println("New User Created By EmailId");
			return O_TKECM_USER.getTKECMU_ID();

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

		O_TKECMUSER.setTKECMU_OTP(oTP);
		O_TKECMUSER.setTKECMU_HASH_KEY(hash);
		O_TKECMUSER.setTKECMU_OTP_EXP((OffsetDateTime.now().plusMinutes(5)));

		session.save(O_TKECMUSER);

		return hash;
	}

	@Override
	public PJ_TKECMUSER isUserPhoneNoAvailable(BigInteger phoneNo) {

		PJ_TKECMUSER O_PJ_TKECMUSER = new PJ_TKECMUSER();

		String getUserByPhone = "FROM TKECMUSER  WHERE TKECMU_PHONE= :phoneNo";

		Query getUserByPhoneQry = O_SessionFactory.getCurrentSession().createQuery(getUserByPhone);
		getUserByPhoneQry.setParameter("phoneNo", phoneNo);

		TKECMUSER O_TKECMUSER = (TKECMUSER) getUserByPhoneQry.uniqueResult();

		if (O_TKECMUSER == null) {

			return null;

		}

		O_PJ_TKECMUSER.setPassword(O_TKECMUSER.getTKECMU_PASSWORD());
		O_PJ_TKECMUSER.setUserId(O_TKECMUSER.getTKECMU_ID());
		O_PJ_TKECMUSER.setStatus(O_TKECMUSER.getTKECMU_STATUS());
		O_PJ_TKECMUSER.setOtpStatus(O_TKECMUSER.getTKECMU_OTP_STATUS());

		return O_PJ_TKECMUSER;

	}

	@Override
	public String createNewUserByPhoneNo(PJ_TKECMUSER RO_UserPojo) {
		Session session = O_SessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		String getUserId = "FROM TKECMUSER ORDER BY TKECMU_ID DESC";

		Query userIdQuery = O_SessionFactory.getCurrentSession().createQuery(getUserId);
		userIdQuery.setMaxResults(1);
		TKECMUSER O_TKECM_USER1 = (TKECMUSER) userIdQuery.uniqueResult();

		TKECMUSER O_TKECM_USER = new TKECMUSER();

		if (O_TKECM_USER1 == null) {

			O_TKECM_USER.setTKECMU_ID("TKECU0001");
		} else {

			String userId = O_TKECM_USER1.getTKECMU_ID();
			Integer Ag = Integer.valueOf(userId.substring(5));
			Ag++;

			System.err.println(Ag);
			O_TKECM_USER.setTKECMU_ID("TKECU" + String.format("%04d", Ag));
		}

		try {

			O_TKECM_USER.setTKECMU_NAME(RO_UserPojo.getName());
			O_TKECM_USER.setTKECMU_OTP(RO_UserPojo.getOtp());
			O_TKECM_USER.setTKECMU_PHONE(RO_UserPojo.getPhoneNo());
			O_TKECM_USER.setTKECMU_PASSWORD(RO_UserPojo.getPassword());
			O_TKECM_USER.setTKECMU_CREATED_DATE(OffsetDateTime.now());
			O_TKECM_USER.setTKECMU_REG_TYPE("M");
			O_TKECM_USER.setTKECMU_COUNTRY_CODE(RO_UserPojo.getCountryCode());
			O_TKECM_USER.setTKECMU_OTP_STATUS("N");
			O_TKECM_USER.setTKECMU_STATUS("N");
			session.save(O_TKECM_USER);
			tx.commit();
			session.close();

			System.out.println("New User Created By PhoneNo");
			return O_TKECM_USER.getTKECMU_ID();

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
	public PJ_TKECMUSER getUserDetailHash(PJ_TKECMUSER RO_UserPojo) {

		String getUserDetail = "FROM TKECMUSER WHERE TKECMU_HASH_KEY =:hashKey";

		Query query = O_SessionFactory.getCurrentSession().createQuery(getUserDetail);
		query.setParameter("hashKey", RO_UserPojo.getHashKey());
		TKECMUSER O_TKECMUSER = (TKECMUSER) query.uniqueResult();

		if (O_TKECMUSER == null) {
			return null;
		}
		PJ_TKECMUSER O_PJ_TKECMUSER = new PJ_TKECMUSER();

		O_PJ_TKECMUSER.setOtp(O_TKECMUSER.getTKECMU_OTP());
		O_PJ_TKECMUSER.setOtpExp(O_TKECMUSER.getTKECMU_OTP_EXP());
		O_PJ_TKECMUSER.setUserId(O_TKECMUSER.getTKECMU_ID());

		return O_PJ_TKECMUSER;
	}

	@Override
	public void changeOTPStatus(String userId) {

		TKECMUSER O_TKECMUSER = O_SessionFactory.getCurrentSession().get(TKECMUSER.class, userId);
		O_TKECMUSER.setTKECMU_STATUS("Y");
		O_TKECMUSER.setTKECMU_OTP_STATUS("Y");
		O_SessionFactory.getCurrentSession().update(O_TKECMUSER);
	}

	@Override
	public void updatePassword(PJ_TKECMUSER o_PJ_TKECMUSER_DETAIL) {

		TKECMUSER O_TKECMUSER = O_SessionFactory.getCurrentSession().get(TKECMUSER.class,
				o_PJ_TKECMUSER_DETAIL.getUserId());
		O_TKECMUSER.setTKECMU_PASSWORD(o_PJ_TKECMUSER_DETAIL.getPassword());
		O_TKECMUSER.setTKECMU_MOD_DATE(OffsetDateTime.now());
		O_SessionFactory.getCurrentSession().update(O_TKECMUSER);

	}

	@Override
	public PJ_TKECTUSERSESSION getApiSecretDataByNewSecret(String apisecret, String userId) {

		TKECMUSER O_TKECMUSER = O_SessionFactory.getCurrentSession().get(TKECMUSER.class, userId);

		Session session = O_SessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		TKECTUSERSESSION O_TKECTUSERSESSION = new TKECTUSERSESSION();
		O_TKECTUSERSESSION.setTKECTUS_USER_ID(O_TKECMUSER);
		O_TKECTUSERSESSION.setTKECTUS_API_KEY(apisecret);
		O_TKECTUSERSESSION.setTKECTUS_CR_DATE(OffsetDateTime.now());
		O_TKECTUSERSESSION.setTKECTUS_ALIVE_YN("Y");
		session.save(O_TKECTUSERSESSION);

		tx.commit();
		session.close();

		PJ_TKECTUSERSESSION O_PJTKECTUSERSESSION = new PJ_TKECTUSERSESSION();

		O_PJTKECTUSERSESSION.setApiKey(O_TKECTUSERSESSION.getTKECTUS_API_KEY());

		return O_PJTKECTUSERSESSION;
	}

	@Override
	public Boolean addAuditDetail(PJ_TKECMUSER O_PJ_TKECMUSER_DETAIL, HttpServletRequest httpServletRequest) {

		TKECTUSERAUDIT O_TKECTUSERAUDIT = new TKECTUSERAUDIT();

		O_TKECTUSERAUDIT.setTKECTUA_LOGIN_TIME(OffsetDateTime.now());
		O_TKECTUSERAUDIT.setTKECTUA_USER_AGENT(httpServletRequest.getHeader("user-agent"));
		O_TKECTUSERAUDIT.setTKECTUA_USER_ID(O_PJ_TKECMUSER_DETAIL.getUserId());
		O_TKECTUSERAUDIT.setTKECTUA_API_KEY(O_PJ_TKECMUSER_DETAIL.getApiKey());
		// O_TKECTUSERAUDIT.setTKECTUA_IP(httpServletRequest.getRemoteHost());

		O_SessionFactory.getCurrentSession().save(O_TKECTUSERAUDIT);

		return true;
	}

	@Override
	public PJ_TKECMUSER getUserDetailAPIKey(String apiKey) {

		PJ_TKECMUSER O_PJ_TKECMUSER = new PJ_TKECMUSER();

		String apiQuery = "FROM TKECTUSERSESSION WHERE TKECTUS_API_KEY =:apikey";

		Query query = O_SessionFactory.getCurrentSession().createQuery(apiQuery);
		query.setParameter("apikey", apiKey);
		TKECTUSERSESSION O_TKECTUSERSESSION = (TKECTUSERSESSION) query.uniqueResult();

		if (O_TKECTUSERSESSION == null) {
			return null;
		}

		O_PJ_TKECMUSER.setName(O_TKECTUSERSESSION.getTKECTUS_USER_ID().getTKECMU_NAME());
		O_PJ_TKECMUSER.setMail(O_TKECTUSERSESSION.getTKECTUS_USER_ID().getTKECMU_MAIL());
		O_PJ_TKECMUSER.setPhoneNo(O_TKECTUSERSESSION.getTKECTUS_USER_ID().getTKECMU_PHONE());
		O_PJ_TKECMUSER.setPassword(O_TKECTUSERSESSION.getTKECTUS_USER_ID().getTKECMU_PASSWORD());
		O_PJ_TKECMUSER.setUserId(O_TKECTUSERSESSION.getTKECTUS_USER_ID().getTKECMU_ID());
		return O_PJ_TKECMUSER;
	}

	@Override
	public Boolean userLogout(String apiKey) {
		Session session = O_SessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		String getApiKey = "FROM TKECTUSERSESSION WHERE TKECTUS_API_KEY =:apiKey";

		try {

			Query getApiKeyQuery = session.createQuery(getApiKey);
			getApiKeyQuery.setParameter("apiKey", apiKey);
			TKECTUSERSESSION O_TKECTUSERSESSION = (TKECTUSERSESSION) getApiKeyQuery.uniqueResult();

			O_TKECTUSERSESSION.setTKECTUS_ALIVE_YN("N");

			session.update(O_TKECTUSERSESSION);

			String getUserId1 = "FROM TKECTUSERAUDIT WHERE TKECTUA_API_KEY =:apikey ";

			Query query1 = session.createQuery(getUserId1);
			query1.setParameter("apikey", apiKey);

			TKECTUSERAUDIT O_TKECTUSERAUDIT = (TKECTUSERAUDIT) query1.uniqueResult();
			O_TKECTUSERAUDIT.setTKECTUA_LOGOUT_TIME((OffsetDateTime.now()));
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
