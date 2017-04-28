package org.razkevich.business.model;

//todo: [] find out which fields are mandatory and make them final
public class FileTransferRq {

	private String fguid;
	private long fileSize;
	private String businessName;
	private String srcFolder;
	private String targetFolder;
	private String spName;
	private String systemId;
	private String bankId;
	private String checkSum;
	private String fileTransferId;
	private String destinationName;
	private String destinationVersion;

	private FileTransferRq() {
	}

	public static class Builder {

		private String fguid;
		private long fileSize;
		private String businessName;
		private String srcFolder;
		private String targetFolder;
		private String spName;
		private String systemId;
		private String bankId;
		private String checkSum;
		private String fileTransferId;
		private String destinationName;
		private String destinationVersion;

		public Builder fguid(String fguid) {
			this.fguid = fguid;
			return this;
		}

		public Builder fileSize(long fileSize) {
			this.fileSize = fileSize;
			return this;
		}

		public Builder businessName(String businessName) {
			this.businessName = businessName;
			return this;
		}

		public Builder srcFolder(String srcFolder) {
			this.srcFolder = srcFolder;
			return this;
		}

		public Builder targetFolder(String targetFolder) {
			this.targetFolder = targetFolder;
			return this;
		}

		public Builder spName(String spName) {
			this.spName = spName;
			return this;
		}

		public Builder systemId(String systemId) {
			this.systemId = systemId;
			return this;
		}

		public Builder bankId(String bankId) {
			this.bankId = bankId;
			return this;
		}

		public Builder checkSum(String checkSum) {
			this.checkSum = checkSum;
			return this;
		}

		public Builder fileTransferId(String fileTransferId) {
			this.fileTransferId = fileTransferId;
			return this;
		}

		public Builder destinationName(String destinationName) {
			this.destinationName = destinationName;
			return this;
		}

		public Builder destinationVersion(String destinationVersion) {
			this.destinationVersion = destinationVersion;
			return this;
		}

		public FileTransferRq build() {
			return new FileTransferRq(this);
		}
	}

	private FileTransferRq(Builder builder) {
		fguid = builder.fguid;
		fileSize = builder.fileSize;
		businessName = builder.businessName;
		srcFolder = builder.srcFolder;
		targetFolder = builder.targetFolder;
		spName = builder.spName;
		systemId = builder.systemId;
		bankId = builder.bankId;
		checkSum = builder.checkSum;
		fileTransferId = builder.fileTransferId;
		destinationName = builder.destinationName;
		destinationVersion = builder.destinationVersion;
	}

	public String getFguid() {
		return fguid;
	}

	public long getFileSize() {
		return fileSize;
	}

	public String getBusinessName() {
		return businessName;
	}

	public String getSrcFolder() {
		return srcFolder;
	}

	public String getTargetFolder() {
		return targetFolder;
	}

	public String getSpName() {
		return spName;
	}

	public String getSystemId() {
		return systemId;
	}

	public String getBankId() {
		return bankId;
	}

	public String getCheckSum() {
		return checkSum;
	}

	public String getFileTransferId() {
		return fileTransferId;
	}

	public String getDestinationName() {
		return destinationName;
	}

	public String getDestinationVersion() {
		return destinationVersion;
	}
}
