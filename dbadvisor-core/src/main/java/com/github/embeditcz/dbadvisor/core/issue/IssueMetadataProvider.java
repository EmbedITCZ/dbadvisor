package com.github.embeditcz.dbadvisor.core.issue;

import java.util.Map;

/**
 * Interface represents strategy for providing custom issue metadata,
 * all beans implementing this interface are automatically used.
 */
public interface IssueMetadataProvider {

    Map<String, Object> getIssueMetadata();

}
