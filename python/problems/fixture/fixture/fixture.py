def total_days(teams):
    if teams % 2 == 0:
        days = teams - 1
    else:
        days = teams
    return days


def empty_fixture(days):
    return [ [] for d in range(0, days) ]


def match_day(team_a, team_b, days):
    if team_b == days:
        team_b = team_a
    return (team_a + team_b) % days


def build(teams):
    n = len(teams)
    days = total_days(n)
    matches = empty_fixture(days)
    for a in range(0, n):
        for b in range(a + 1, n):
            day = match_day(a, b, days)
            match = [teams[a], teams[b]]
            matches[day].append(match)
    return matches
