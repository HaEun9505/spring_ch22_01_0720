package com.haeun.ch0720.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.haeun.ch0720.dto.TicketDto;

//데이터 접속 객체
public class TicketDao {
	JdbcTemplate template;
	
	PlatformTransactionManager transactionManager;
	
	public void setTemplate(JdbcTemplate template) {
		this.template = template;
	}
	
	//bean으로 생성한 객체가 들어옴
	public void setTransactionManager(PlatformTransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}

	public TicketDao() {
		super();
		
	}
	
	public void buyTicket(final TicketDto dto) {
		//두개의 작업을 하나의 트랜잭션으로 묶기(두개가 모두 성공이 되어야지만 트랜잭션이 완료됨)
		TransactionDefinition definition = new DefaultTransactionDefinition();
		TransactionStatus status = transactionManager.getTransaction(definition);
		
		try {
			template.update(new PreparedStatementCreator() {
				
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					
					String sql = "INSERT INTO card(consumerid, amount) VALUES(?, ?)";
					PreparedStatement pstmt = con.prepareStatement(sql);
					//buyTicket 호출할때 TicketDto의 Dto를 받음
					pstmt.setString(1, dto.getConsumerid());
					pstmt.setInt(2, dto.getAmount());
					return pstmt;
				}
			});
			
			template.update(new PreparedStatementCreator() {
				
				@Override
				public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
					
					String sql = "INSERT INTO ticket(consumerid, countnum) VALUES(?, ?)";
					PreparedStatement pstmt = con.prepareStatement(sql);
					//buyTicket 호출할때 TicketDto의 Dto를 받음
					pstmt.setString(1, dto.getConsumerid());
					pstmt.setInt(2, dto.getAmount());
					
					return pstmt;
				}
			});
			
			transactionManager.commit(status); //두개의 작업이 모두 성공해야 commit
			
		}catch (Exception e) {
			e.printStackTrace();
			
			transactionManager.rollback(status);	//한개라도 실패 시 취소(롤백)
		}
		
		
	}
}
