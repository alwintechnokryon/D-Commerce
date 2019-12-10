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

		ModelMapper O_ModelMapper = new ModelMapper();

		String getUserByEmail = "FROM TKECMUSER WHERE tkecmuMail= :email";

		Query getUserByEmailQry = O_SessionFactory.getCurrentSession().createQuery(getUserByEmail);
		getUserByEmailQry.setParameter("email", mail);

		TKECMUSER O_TKECMUSER = (TKECMUSER) getUserByEmailQry.uniqueResult();

		if (O_TKECMUSER == null) {

			return null;

		}

		PJ_TKECMUSER O_PJ_TKECMUSER = O_ModelMapper.map(O_TKECMUSER, PJ_TKECMUSER.class);

//		O_PJ_TKECMUSER.setTkecmuPassword(O_TKECMUSER.getTkecmuPassword());
//		O_PJ_TKECMUSER.setTkecmuId(O_TKECMUSER.getTkecmuId());
//		O_PJ_TKECMUSER.setTkecmuStatus(O_TKECMUSER.getTkecmuStatus());
//		O_PJ_TKECMUSER.setTkecmuOtpStatus(O_TKECMUSER.getTkecmuOtpStatus());

		return O_PJ_TKECMUSER;

	}

	@Override
	public String createNewUserByEmail(PJ_TKECMUSER RO_UserPojo) {
		Session session = O_SessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		String getUserId = "FROM TKECMUSER ORDER BY tkecmuId DESC";

		Query userIdQuery = O_SessionFactory.getCurrentSession().createQuery(getUserId);
		userIdQuery.setMaxResults(1);
		TKECMUSER O_TKECM_USER1 = (TKECMUSER) userIdQuery.uniqueResult();

		TKECMUSER O_TKECM_USER = new TKECMUSER();

		if (O_TKECM_USER1 == null) {

			O_TKECM_USER.setTkecmuId("TKECU0001");
		} else {

			String userId = O_TKECM_USER1.getTkecmuId();
			Integer Ag = Integer.valueOf(userId.substring(5));
			Ag++;

			System.err.println(Ag);
			O_TKECM_USER.setTkecmuId("TKECU" + String.format("%04d", Ag));
		}

		try {

			O_TKECM_USER.setTkecmuName(RO_UserPojo.getTkecmuName());
			O_TKECM_USER.setTkecmuMail(RO_UserPojo.getTkecmuMail());
			O_TKECM_USER.setTkecmuPassword(RO_UserPojo.getTkecmuPassword());
			O_TKECM_USER.setTkecmuCreatedDate(OffsetDateTime.now());
			O_TKECM_USER.setTkecmuRegType("E");
			O_TKECM_USER.setTkecmuStatus("N");
			O_TKECM_USER.setTkecmuOtpStatus("N");
			session.save(O_TKECM_USER);
			tx.commit();
			session.close();

			System.out.println("New User Created By EmailId");
			return O_TKECM_USER.getTkecmuId();

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

		O_TKECMUSER.setTkecmuOtp(oTP);
		O_TKECMUSER.setTkecmuHashKey(hash);
		O_TKECMUSER.setTkecmuOtpExp((OffsetDateTime.now().plusMinutes(5)));

		session.save(O_TKECMUSER);

		return hash;
	}

	@Override
	public PJ_TKECMUSER isUserPhoneNoAvailable(BigInteger phoneNo) {

		ModelMapper O_ModelMapper = new ModelMapper();

		String getUserByPhone = "FROM TKECMUSER  WHERE tkecmuPhone= :phoneNo";

		Query getUserByPhoneQry = O_SessionFactory.getCurrentSession().createQuery(getUserByPhone);
		getUserByPhoneQry.setParameter("phoneNo", phoneNo);

		TKECMUSER O_TKECMUSER = (TKECMUSER) getUserByPhoneQry.uniqueResult();

		if (O_TKECMUSER == null) {

			return null;

		}

		PJ_TKECMUSER O_PJ_TKECMUSER = O_ModelMapper.map(O_TKECMUSER, PJ_TKECMUSER.class);

//		O_PJ_TKECMUSER.setTkecmuPassword(O_TKECMUSER.getTkecmuPassword());
//		O_PJ_TKECMUSER.setTkecmuId(O_TKECMUSER.getTkecmuId());
//		O_PJ_TKECMUSER.setTkecmuStatus(O_TKECMUSER.getTkecmuStatus());
//		O_PJ_TKECMUSER.setTkecmuOtpStatus(O_TKECMUSER.getTkecmuOtpStatus());

		return O_PJ_TKECMUSER;

	}

	@Override
	public String createNewUserByPhoneNo(PJ_TKECMUSER RO_UserPojo) {
		Session session = O_SessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		String getUserId = "FROM TKECMUSER ORDER BY tkecmuId DESC";

		Query userIdQuery = O_SessionFactory.getCurrentSession().createQuery(getUserId);
		userIdQuery.setMaxResults(1);
		TKECMUSER O_TKECM_USER1 = (TKECMUSER) userIdQuery.uniqueResult();

		TKECMUSER O_TKECM_USER = new TKECMUSER();

		if (O_TKECM_USER1 == null) {

			O_TKECM_USER.setTkecmuId("TKECU0001");
		} else {

			String userId = O_TKECM_USER1.getTkecmuId();
			Integer Ag = Integer.valueOf(userId.substring(5));
			Ag++;

			System.err.println(Ag);
			O_TKECM_USER.setTkecmuId("TKECU" + String.format("%04d", Ag));
		}

		try {

			O_TKECM_USER.setTkecmuName(RO_UserPojo.getTkecmuName());
			O_TKECM_USER.setTkecmuOtp(RO_UserPojo.getTkecmuOtp());
			O_TKECM_USER.setTkecmuPhone(RO_UserPojo.getTkecmuPhone());
			O_TKECM_USER.setTkecmuPassword(RO_UserPojo.getTkecmuPassword());
			O_TKECM_USER.setTkecmuCreatedDate(OffsetDateTime.now());
			O_TKECM_USER.setTkecmuRegType("M");
			O_TKECM_USER.setTkecmuCountryCode(RO_UserPojo.getTkecmuCountryCode());
			O_TKECM_USER.setTkecmuOtpStatus("N");
			O_TKECM_USER.setTkecmuStatus("N");
			session.save(O_TKECM_USER);
			tx.commit();
			session.close();

			System.out.println("New User Created By PhoneNo");
			return O_TKECM_USER.getTkecmuId();

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

		String getUserDetail = "FROM TKECMUSER WHERE tkecmuHashKey =:hashKey";

		ModelMapper O_ModelMapper = new ModelMapper();

		Query query = O_SessionFactory.getCurrentSession().createQuery(getUserDetail);
		query.setParameter("hashKey", RO_UserPojo.getTkecmuHashKey());
		TKECMUSER O_TKECMUSER = (TKECMUSER) query.uniqueResult();

		if (O_TKECMUSER == null) {
			return null;
		}
		PJ_TKECMUSER O_PJ_TKECMUSER = O_ModelMapper.map(O_TKECMUSER, PJ_TKECMUSER.class);

//		O_PJ_TKECMUSER.setTkecmuOtp(O_TKECMUSER.getTkecmuOtp());
//		O_PJ_TKECMUSER.setTkecmuOtpExp(O_TKECMUSER.getTkecmuOtpExp());
//		O_PJ_TKECMUSER.setTkecmuId(O_TKECMUSER.getTkecmuId());

		return O_PJ_TKECMUSER;
	}

	@Override
	public void changeOTPStatus(String userId) {

		TKECMUSER O_TKECMUSER = O_SessionFactory.getCurrentSession().get(TKECMUSER.class, userId);
		O_TKECMUSER.setTkecmuStatus("Y");
		O_TKECMUSER.setTkecmuOtpStatus("Y");
		O_SessionFactory.getCurrentSession().update(O_TKECMUSER);
	}

	@Override
	public void updatePassword(PJ_TKECMUSER o_PJ_TKECMUSER_DETAIL) {

		TKECMUSER O_TKECMUSER = O_SessionFactory.getCurrentSession().get(TKECMUSER.class,
				o_PJ_TKECMUSER_DETAIL.getTkecmuId());
		O_TKECMUSER.setTkecmuPassword(o_PJ_TKECMUSER_DETAIL.getTkecmuPassword());
		O_TKECMUSER.setTkecmuModifideDate(OffsetDateTime.now());
		O_SessionFactory.getCurrentSession().update(O_TKECMUSER);

	}

	@Override
	public PJ_TKECTUSERSESSION getApiSecretDataByNewSecret(String apisecret, String userId) {

		TKECMUSER O_TKECMUSER = O_SessionFactory.getCurrentSession().get(TKECMUSER.class, userId);

		Session session = O_SessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		TKECTUSERSESSION O_TKECTUSERSESSION = new TKECTUSERSESSION();
		O_TKECTUSERSESSION.setTkectusUserId(O_TKECMUSER);
		O_TKECTUSERSESSION.setTkectusApiKey(apisecret);
		O_TKECTUSERSESSION.setTkectusCreatedDate(OffsetDateTime.now());
		O_TKECTUSERSESSION.setTkectusAliveYN("Y");
		session.save(O_TKECTUSERSESSION);

		tx.commit();
		session.close();

		PJ_TKECTUSERSESSION O_PJTKECTUSERSESSION = new PJ_TKECTUSERSESSION();

		O_PJTKECTUSERSESSION.setTkectusApiKey(O_TKECTUSERSESSION.getTkectusApiKey());

		return O_PJTKECTUSERSESSION;
	}

	@Override
	public Boolean addAuditDetail(PJ_TKECMUSER O_PJ_TKECMUSER_DETAIL, HttpServletRequest httpServletRequest) {

		TKECTUSERAUDIT O_TKECTUSERAUDIT = new TKECTUSERAUDIT();

		O_TKECTUSERAUDIT.setTkectuaLoginTime(OffsetDateTime.now());
		O_TKECTUSERAUDIT.setTkectuaUserAgent(httpServletRequest.getHeader("user-agent"));
		O_TKECTUSERAUDIT.setTkectuaUserId(O_PJ_TKECMUSER_DETAIL.getTkecmuId());
		O_TKECTUSERAUDIT.setTkectuaApiKey(O_PJ_TKECMUSER_DETAIL.getApiKey());
		// O_TKECTUSERAUDIT.setTKECTUA_IP(httpServletRequest.getRemoteHost());

		O_SessionFactory.getCurrentSession().save(O_TKECTUSERAUDIT);

		return true;
	}

	@Override
	public PJ_TKECMUSER getUserDetailAPIKey(String apiKey) {

		ModelMapper O_ModelMapper = new ModelMapper();
		String apiQuery = "FROM TKECTUSERSESSION WHERE tkectusApiKey =:apikey";

		Query query = O_SessionFactory.getCurrentSession().createQuery(apiQuery);
		query.setParameter("apikey", apiKey);
		TKECTUSERSESSION O_TKECTUSERSESSION = (TKECTUSERSESSION) query.uniqueResult();

		if (O_TKECTUSERSESSION == null) {
			return null;
		}
		// TKECMUSER O_TKECMUSER = O_TKECTUSERSESSION.getTkectusUserId();
		// PJ_TKECMUSER O_PJ_TKECMUSER = O_ModelMapper.map(O_TKECMUSER,
		// PJ_TKECMUSER.class);

		PJ_TKECMUSER O_PJ_TKECMUSER = new PJ_TKECMUSER();

		O_PJ_TKECMUSER.setTkecmuName(O_TKECTUSERSESSION.getTkectusUserId().getTkecmuName());
		O_PJ_TKECMUSER.setTkecmuMail(O_TKECTUSERSESSION.getTkectusUserId().getTkecmuMail());
		O_PJ_TKECMUSER.setTkecmuPhone(O_TKECTUSERSESSION.getTkectusUserId().getTkecmuPhone());
		O_PJ_TKECMUSER.setTkecmuPassword(O_TKECTUSERSESSION.getTkectusUserId().getTkecmuPassword());
		O_PJ_TKECMUSER.setTkecmuId(O_TKECTUSERSESSION.getTkectusUserId().getTkecmuId());
		return O_PJ_TKECMUSER;
	}

	@Override
	public Boolean userLogout(String apiKey) {
		Session session = O_SessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		String getApiKey = "FROM TKECTUSERSESSION WHERE tkectusApiKey =:apiKey";

		try {

			Query getApiKeyQuery = session.createQuery(getApiKey);
			getApiKeyQuery.setParameter("apiKey", apiKey);
			TKECTUSERSESSION O_TKECTUSERSESSION = (TKECTUSERSESSION) getApiKeyQuery.uniqueResult();

			O_TKECTUSERSESSION.setTkectusAliveYN("N");

			session.update(O_TKECTUSERSESSION);

			String getUserId1 = "FROM TKECTUSERAUDIT WHERE tkectuaApiKey =:apikey ";

			Query query1 = session.createQuery(getUserId1);
			query1.setParameter("apikey", apiKey);

			TKECTUSERAUDIT O_TKECTUSERAUDIT = (TKECTUSERAUDIT) query1.uniqueResult();
			O_TKECTUSERAUDIT.setTkectuaLogouttime((OffsetDateTime.now()));
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
