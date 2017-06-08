### Fixture

Write an algorithm to build a fixture for a list of teams.
Each team should play all of the rivals once and the tournament duration should be the shortest possible.

For instance, given the list of players: 

```python
players = ['Roger Federer', 'Rafael Nadal', 'Andy Murray', 'Novak Djokovic']
```

One possible fixture is:

| Day   | Match 1                         | Match 2                        |
|-------|---------------------------------|--------------------------------|
| Day 1 | Roger Federer vs Novak Djokovic | Rafael Nadal vs Andy Murray    |
| Day 2 | Roger Federer vs Rafael Nadal   | Andy Murray vs Novak Djokovic  |
| Day 3 | Roger Federer vs Andy Murray    | Rafael Nadal vs Novak Djokovic |


### Fixture Home and Away

Same as before, but each team should play each of the rivals twice: once as home team and once as away team.

For instance, given the list of teams:

```python
teams = ['FC Barcelona', 'Real Madrid CF', 'Manchester United FC', 'Chelsea FC']
```

One possible fixture is:

| Day   | Match 1                              | Match 2                                |
|-------|--------------------------------------|----------------------------------------|
| Day 1 | FC Barcelona vs Chelsea FC           | Real Madrid CF vs Manchester United FC |
| Day 2 | Real Madrid CF vs FC Barcelona       | Chelsea FC vs Manchester United FC     |
| Day 3 | FC Barcelona vs Manchester United FC | Real Madrid CF vs Chelsea FC           |
| Day 4 | Chelsea FC vs FC Barcelona           | Manchester United FC vs Real Madrid CF |
| Day 5 | FC Barcelona vs Real Madrid CF       | Manchester United FC vs Chelsea FC     |
| Day 6 | Manchester United FC vs FC Barcelona | Chelsea FC vs Real Madrid CF           |
