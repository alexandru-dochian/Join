package join.storage;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class FileEntity {

    @Id
    private String fileName;
    private String downloadLink;
    private String deleteLink;
    private String userWhoUploaded;
    private int nrOfDownloads;

    public FileEntity(String fileName, String userWhoUploaded) {
        this.fileName = fileName;
        this.userWhoUploaded = userWhoUploaded;
        this.deleteLink = String.format("/files/delete/%s", fileName);
        this.downloadLink = String.format("/files/download/%s", fileName);
        this.nrOfDownloads = 0;
    }

    public FileEntity() {}

    public FileEntity wasDownloaded()
    {
        nrOfDownloads++;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getUserWhoUploaded() {
        return userWhoUploaded;
    }

    public void setUserWhoUploaded(String userWhoUploaded) {
        this.userWhoUploaded = userWhoUploaded;
    }

    public int getNrOfDownloads() {
        return nrOfDownloads;
    }

    public void setNrOfDownloads(int nrOfDownloads) {
        this.nrOfDownloads = nrOfDownloads;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public String getDeleteLink() {
        return deleteLink;
    }

    public void setDeleteLink(String deleteLink) {
        this.deleteLink = deleteLink;
    }

    @Override
    public String toString() {
        return "FileEntity{" +
                "fileName='" + fileName + '\'' +
                ", userWhoUploaded='" + userWhoUploaded + '\'' +
                ", nrOfDownloads=" + nrOfDownloads +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileEntity that = (FileEntity) o;
        return fileName.equals(that.fileName) &&
                userWhoUploaded.equals(that.userWhoUploaded);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fileName, userWhoUploaded);
    }
}
