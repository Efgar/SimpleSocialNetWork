package com.efgh.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.UUID;

@Data
@Setter(AccessLevel.PRIVATE)
public class Post implements Comparable<Post> {
    private String content;
    private Date creationDate;

    @EqualsAndHashCode.Include
    private String uniqueID = UUID.randomUUID().toString();

    Post(String postText) {
        Assert.isTrue(StringUtils.isNotBlank(postText), "Post text can not be empty");
        Assert.isTrue(postText.length() < 140, "Post length can not be more than 140 characters");
        setContent(postText);
        setCreationDate(new Date());
    }

    public int compareTo(Post postToCompare) {
        int comparisonResult = getCreationDate().compareTo(postToCompare.getCreationDate());

        if (comparisonResult == 0) {
            comparisonResult = getContent().compareTo(postToCompare.getContent());
        }
        if (comparisonResult == 0) {
            comparisonResult = getUniqueID().compareTo(postToCompare.getUniqueID());
        }

        return comparisonResult;
    }
}
