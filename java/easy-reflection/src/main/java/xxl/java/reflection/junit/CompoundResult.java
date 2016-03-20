package xxl.java.reflection.junit;

import static java.lang.String.format;

import java.util.*;

import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class CompoundResult extends Result {

	public static Collection<Result> filter(Collection<Result> results, boolean passing) {
		Collection<Result> filtered = new LinkedList<Result>();
		for (Result result : results) {
			if (result.wasSuccessful() == passing) {
				filtered.add(result);
			}
		}
		return filtered;
	}

	public CompoundResult(Collection<Result> results) {
		this.results = results;
	}

	@Override
	public boolean wasSuccessful() {
		return getFailureCount() == 0;
	}

	@Override
	public int getFailureCount() {
		return failures().size();
	}

	@Override
	public int getRunCount() {
		return results().size();
	}

	@Override
	public List<Failure> getFailures() {
		List<Failure> failures = new ArrayList<Failure>(getFailureCount());
		for (Result failure : failures()) {
			failures.addAll(failure.getFailures());
		}
		return failures;
	}

	@Override
	public int getIgnoreCount() {
		int total = 0;
		for (Result result : results()) {
			total += result.getIgnoreCount();
		}
		return total;
	}

	@Override
	public long getRunTime() {
		long total = 0;
		for (Result result : results()) {
			total += result.getRunTime();
		}
		return total;
	}

	public Collection<Result> results() {
		return results;
	}

	public Collection<Result> failures() {
		if (failures == null) {
			failures = filter(results(), false);
		}
		return failures;
	}

	public Collection<Result> successes() {
		if (successes == null) {
			successes = filter(results(), true);
		}
		return successes;
	}

	@Override
	public String toString() {
		return format("%s(failures: %d/%d)", getClass().getSimpleName(), getFailureCount(), getRunCount());
	}

	private Collection<Result> results;
	private Collection<Result> failures;
	private Collection<Result> successes;

	static final long serialVersionUID = -1552304533986082936L;
}
