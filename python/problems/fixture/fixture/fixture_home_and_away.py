def total_days(teams):
    if teams % 2 == 0:
        days = teams - 1
    else:
        days = teams
    return days


def empty_fixture(days):
    return [ [] for d in range(0, 2*days) ]


def match_day(team_a, team_b, days):
    if team_b == days:
        team_b = team_a
    return (team_a + team_b) % days


def match_for(team_a, team_b, days):
    day = match_day(team_a, team_b, days)
    minim = min(team_a, team_b)
    maxim = max(team_a, team_b)
    if day % 2 == 0:
        match = (minim, maxim)
    else:
        match = (maxim, minim)
    return (day, match[0], match[1])


def build(teams):
    n = len(teams)
    days = total_days(n)
    matches = empty_fixture(days)
    for a in range(0, n):
        for b in range(a + 1, n):
            (day, home, away) = match_for(a, b, days)
            matches[day].append([teams[home], teams[away]])
            matches[days + day].append([teams[away], teams[home]])
    return matches
