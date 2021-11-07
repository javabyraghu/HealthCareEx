package in.nareshit.raghu.service;

import java.util.List;

import in.nareshit.raghu.entity.Document;

public interface IDocumentService {

	void saveDocument(Document doc);
	List<Object[]> getDocumentIdAndName();
	void deleteDocumentById(Long id);
	Document getDocumentById(Long id);
}
