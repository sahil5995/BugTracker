package de.bugtracker.filter;

import de.bugtracker.domainobject.BugDO;
import de.bugtracker.domainvalue.BugStatus;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BugFilter {

    public static Predicate<BugDO> withStatus(String status) {
        return b -> status == null || b.getStatus() == BugStatus.valueOf(status);
    }

    public static Predicate<BugDO> withUser(String user) {
        return b -> {
            if (user != null) {
              return b.getAssignedUser() != null && b.getAssignedUser().equals(user);
            } else return true;
        };
    }

    public static List<BugDO> filterBugs(List<BugDO> bugs, Set<Predicate<BugDO>> predicates) {
        Predicate<BugDO> finalPredicate  = chainPredicates(predicates);
        return bugs.stream()
                .filter(finalPredicate)
                .collect(Collectors.<BugDO>toList());
    }

    private static Predicate<BugDO> chainPredicates(Set<Predicate<BugDO>> predicates) {
        return predicates.stream()
                .reduce(Predicate::and)
                .orElse(p -> false);
    }

}