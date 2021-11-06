package in.nareshit.raghu.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="doc_tab")
public class Document {
	@Id
	@Column(name="doc_id_col")
	private Long docId;

	@Column(name="doc_name_col")
	private String docName;
	
	@Lob
	@Column(name="doc_data_col")
	private byte[] docData;
}
