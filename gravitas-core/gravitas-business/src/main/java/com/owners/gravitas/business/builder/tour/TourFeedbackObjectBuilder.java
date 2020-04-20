package com.owners.gravitas.business.builder.tour;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.util.CollectionUtils;

import com.zuner.coshopping.model.tour_feedback.File;
import com.zuner.coshopping.model.tour_feedback.FileUploadStatus;
import com.zuner.coshopping.model.tour_feedback.Note;
import com.zuner.coshopping.model.tour_feedback.Reaction;
import com.zuner.coshopping.model.tour_feedback.TourFeedback;

/**
 * The Class TourFeedbackObjectBuilder .
 *
 * @author rajputbh
 */
public class TourFeedbackObjectBuilder {
    private TourFeedback tourFeedback;

    public TourFeedbackObjectBuilder() {
        tourFeedback = new TourFeedback();
    }

    public TourFeedbackObjectBuilder setPropertyId(String propertyId) {
        this.tourFeedback.setPropertyId(propertyId);
        return this;
    }

    public TourFeedbackObjectBuilder setBuyerId(String buyerId) {
        this.tourFeedback.setBuyerId(buyerId);
        return this;
    }

    public TourFeedbackObjectBuilder setTaskId(String taskId) {
        this.tourFeedback.setTaskId(taskId);
        return this;
    }

    public TourFeedbackObjectBuilder setAgentId(String agentId) {
        this.tourFeedback.setAgentId(agentId);
        return this;
    }

    public TourFeedbackObjectBuilder setId(String id) {
        this.tourFeedback.setId(id);
        return this;
    }

    public TourFeedbackObjectBuilder setOverallRating(Float overallRating) {
        this.tourFeedback.setOverallRating(overallRating);
        return this;
    }

    public TourFeedbackObjectBuilder setOverallComment(String overallComment) {
        this.tourFeedback.setOverallComment(overallComment);
        return this;
    }

    public TourFeedbackObjectBuilder setIsEmailSent(Boolean isEmailSent) {
        this.tourFeedback.setIsEmailSent(isEmailSent);
        return this;
    }

    public TourFeedbackObjectBuilder setTotalFileCount(Integer totalFileCount) {
        this.tourFeedback.setTotalFileCount(totalFileCount);
        return this;
    }

    public TourFeedbackObjectBuilder setUploadedFileCount(Integer uploadedFileCount) {
        this.tourFeedback.setUploadedFileCount(uploadedFileCount);
        return this;
    }

    public TourFeedbackObjectBuilder setCreatedOn(Long createdOn) {
        this.tourFeedback.setCreatedOn(createdOn);
        return this;
    }

    public TourFeedbackObjectBuilder setUpdatedOn(Long updatedOn) {
        this.tourFeedback.setUpdatedOn(updatedOn);
        return this;
    }

    public TourFeedbackObjectBuilder setNotes(List<Note> notes) {
        this.tourFeedback.setNotes(notes);
        return this;
    }

    public TourFeedbackObjectBuilder from(final TourFeedback tourFeedbackRequest, String agentId) {
        if (tourFeedbackRequest != null) {
            this.setPropertyId(tourFeedbackRequest.getPropertyId());
            this.setId(UUID.randomUUID().toString());
            if (tourFeedbackRequest.getCreatedOn() == null) {
                this.setCreatedOn(System.currentTimeMillis());
            } else {
                this.setCreatedOn(tourFeedbackRequest.getCreatedOn());
            }
            if (tourFeedbackRequest.getUpdatedOn() == null) {
                this.setUpdatedOn(System.currentTimeMillis());
            } else {
                this.setUpdatedOn(tourFeedbackRequest.getUpdatedOn());
            }
            this.setBuyerId(tourFeedbackRequest.getBuyerId());
            this.setTaskId(tourFeedbackRequest.getTaskId());
            this.setAgentId(agentId);
            this.setOverallRating(tourFeedbackRequest.getOverallRating());
            this.setOverallComment(tourFeedbackRequest.getOverallComment());
            this.setIsEmailSent(false);
            this.setTotalFileCount(0);
            this.setNotes(getNotes(tourFeedbackRequest.getNotes()));
        }
        return this;
    }

    private List<Note> getNotes(List<Note> notesList) {
        if (!CollectionUtils.isEmpty(notesList)) {
            List<Note> notes = new ArrayList<Note>();
            for (Note noteRequest : notesList) {
                if (noteRequest != null) {
                    Note note = new Note();
                    note.setComment(noteRequest.getComment());
                    if (noteRequest.getCreatedOn() == null) {
                        note.setCreatedOn(System.currentTimeMillis());
                    } else {
                        note.setCreatedOn(noteRequest.getCreatedOn());
                    }
                    if (noteRequest.getUpdatedOn() == null) {
                        note.setUpdatedOn(System.currentTimeMillis());
                    } else {
                        note.setUpdatedOn(noteRequest.getUpdatedOn());
                    }
                    note.setId(UUID.randomUUID().toString());
                    if (noteRequest.getReaction() != null) {
                        note.setReaction(Reaction.valueOf(noteRequest.getReaction().name()));
                    }
                    note.setFiles(getFiles(noteRequest.getFiles()));
                    notes.add(note);
                }
            }
            return notes;
        }
        return null;
    }

    private List<File> getFiles(List<File> fileRequestList) {
        if (!CollectionUtils.isEmpty(fileRequestList)) {
            List<File> files = new ArrayList<File>();
            for (File fileRequest : fileRequestList) {
                if (fileRequest != null) {
                    File file = new File();
                    file.setNameCode(fileRequest.getNameCode());
                    file.setId(UUID.randomUUID().toString());
                    file.setType(fileRequest.getType());
                    if (fileRequest.getCreatedOn() == null) {
                        file.setCreatedOn(System.currentTimeMillis());
                    } else {
                        file.setCreatedOn(fileRequest.getCreatedOn());
                    }
                    if (fileRequest.getUpdatedOn() == null) {
                        file.setUpdatedOn(System.currentTimeMillis());
                    } else {
                        file.setUpdatedOn(fileRequest.getUpdatedOn());
                    }
                    file.setUploadStatus(FileUploadStatus.PENDING);
                    files.add(file);
                }
            }
            return files;
        }
        return null;
    }

    public TourFeedback build() {
        return this.tourFeedback;
    }
}
