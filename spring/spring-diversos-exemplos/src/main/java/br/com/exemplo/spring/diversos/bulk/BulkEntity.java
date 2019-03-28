package br.com.exemplo.spring.diversos.bulk;

import java.io.Serializable;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bulk")
public class BulkEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2345798652340715200L;

	@Id
	private long id;

	private String description;

	@Column(name = "CREATION_DATE")
	private OffsetDateTime creationDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public OffsetDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(OffsetDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BulkEntity(String description, OffsetDateTime creationDate) {
		super();
		this.description = description;
		this.creationDate = creationDate;
	}

	public BulkEntity(long id, String description, OffsetDateTime creationDate) {
		super();
		this.id = id;
		this.description = description;
		this.creationDate = creationDate;
	}

	public BulkEntity() {
		super();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BulkEntity [id=").append(id).append(", description=").append(description)
				.append(", creationDate=").append(creationDate).append("]");
		return builder.toString();
	}

}
