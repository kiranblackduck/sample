package com.synopsys.integration.scm.api.pagination;

import com.synopsys.integration.scm.tools.enums.*;
import com.synopsys.integration.scm.tools.exception.ScmException;

import java.time.Instant;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

public abstract class SCMFilter {

    public Map<String, String> filterMap;

    public Map<String, String> getFilterMap() {
        return filterMap;
    }

    public abstract EnumSet<FilterEnum> getFilters();

    public EnumSet<TypeEnum> getTypes() throws ScmException {
        throw new UnsupportedOperationException();
    }

    public EnumSet<StateEnum> getStates() throws ScmException {
        throw new UnsupportedOperationException();
    }

    public EnumSet<PermissionEnum> getPermissions() throws ScmException {
        throw new UnsupportedOperationException();
    }

    public EnumSet<ArchiveEnum> getArchives() throws ScmException {
        throw new UnsupportedOperationException();
    }

    public EnumSet<VisibilityEnum> getVisibilities() throws ScmException {
        throw new UnsupportedOperationException();
    }

    public EnumSet<AffiliationEnum> getAffiliations() throws ScmException {
        throw new UnsupportedOperationException();
    }

    public EnumSet<MinAccessLevelEnum> getMinAccessLevels() throws ScmException {
        throw new UnsupportedOperationException();
    }

    public EnumSet<OwnerEnum> getOwner() throws ScmException {
        throw new UnsupportedOperationException();
    }

    public EnumSet<RoleEnum> getRoles() throws ScmException {
        throw new UnsupportedOperationException();
    }

    public SCMFilter addType(TypeEnum typeEnum) throws ScmException {
        throw new UnsupportedOperationException();
    }

    public SCMFilter addSince(Instant date) throws ScmException {
        throw new UnsupportedOperationException();
    }

    public SCMFilter addBefore(Instant date) throws ScmException {
        throw new UnsupportedOperationException();
    }

    public SCMFilter addRole(RoleEnum roleEnum) throws ScmException {
        throw new UnsupportedOperationException();
    }

    public SCMFilter addAffiliations(AffiliationEnum affiliationEnum1, AffiliationEnum affiliationEnum2) throws ScmException {
        throw new UnsupportedOperationException();
    }

    public SCMFilter addVisibility(VisibilityEnum visibilityEnum) throws ScmException {
        throw new UnsupportedOperationException();
    }

    public SCMFilter addAffiliation(AffiliationEnum affiliationEnum) throws ScmException {
        throw new UnsupportedOperationException();
    }

    public SCMFilter addMinAccessLevel(MinAccessLevelEnum minAccessLevelEnum) throws ScmException {
        throw new UnsupportedOperationException();
    }

    public SCMFilter addOwnerFilter(OwnerEnum ownerEnum) throws ScmException {
        throw new UnsupportedOperationException();
    }

    public SCMFilter addAfter(Instant date) throws ScmException {
        throw new UnsupportedOperationException();
    }

    public SCMFilter addArchived(ArchiveEnum archiveEnum) throws ScmException {
        throw new UnsupportedOperationException();
    }

    public SCMFilter addState(StateEnum stateEnum) throws ScmException {
        throw new UnsupportedOperationException();
    }

    public SCMFilter addPermission(PermissionEnum permissionEnum) throws ScmException {
        throw new UnsupportedOperationException();
    }


    public List<VisibilityEnum> getVisibilityList() {
        throw new UnsupportedOperationException();
    }
    
    public OrganizationTypeEnum getOrganizationType() {
        throw new UnsupportedOperationException();
    }

    public String getSearchTerm() {
        throw new UnsupportedOperationException();
    }

    public String getOrganizationName() {
        throw new UnsupportedOperationException();
    }

    public List<String> getIncludeValues(String criterionName) {
        throw new UnsupportedOperationException();
    }

    public List<String> getExcludeValues(String criterionName) {
        throw new UnsupportedOperationException();
    }
}
