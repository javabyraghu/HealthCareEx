package in.nareshit.raghu.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:RAGHU SIR 
 *  Generated F/w:SHWR-Framework 
 */
@Entity
@Table(name = "appointment_tab")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="app_doc_id_fk_col")
	private Doctor doctor;
	
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	@Temporal(TemporalType.DATE)
	@Column(name="app_dte_col")
	private Date date;
	
	@Column(name="app_slots_col")
	private Integer noOfSlots;
	
	@Column(name="app_dtls_col")
	private String details;
	
	@Column(name="app_amt_col")
	private Double fee;
}
