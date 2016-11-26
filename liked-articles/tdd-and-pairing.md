### [How TDD and Pairing Increase Production](http://anarchycreek.com/2009/05/26/how-tdd-and-pairing-increase-production/)

If you want more production, look first to raising your internal quality.

1. Because internal quality and external quality are not the same things.

2. Because the biggest single determinant in today’s production is the quality of yesterday’s production.

3. Because typing is not now and never has been the bottleneck in writing code.

### 1. Internal vs. External Quality

External quality is about the “saleability” of the software you’re making.  If there were a number to represent how many features you have, or whether those features are correct across the entire range of possible inputs, that number would be the external quality.  External quality can be traded for time-to-market. It’s not traded for productivity per se, but it’s a perfectly delightful way of hitting your ship-date.

Internal quality, on the other hand, is about the code.  It’s about crossing your t’s and dotting your i’s.  It’s merciless refactoring, delightful pairing, and using microtests to drive the code. You can not go faster by shorting internal quality.

### 2. Yesterday Determines Today

All day long, every time you make a move, you will be depending on the code that’s already there.  Every line of it you have to study will slow you down.  Every extra open dependency will slow you down.  Every bad variable name will slow you down. Every flawed design decision, be it big or small, will slow you down. If you want to work as fast as you can, you want to work with clean code.

### 3. Typing Is Not The Bottleneck

The most common reaction to pairing and TDD is to suggest that they’ll really slow the team down:

* If two people sit at one machine, that’s got to mean that we have 50% less hardware utilization, which has just got to hurt our production.
* On TDD: tests are code.  If I can only produce 100 lines of code a day, and you expect me to have that code driven by tests, you’re basically telling me to write just 50 lines of code and 50 lines of test.

So it could not possibly be the case that TDD and pairing will help my production.
Yes. Two people together can code as fast as two people separately because, get this, the hard part of coding isn’t the part where you type.  When people say they ‘coded’ all day, what do they really mean? They are usually saying they:

* typed in code
* drew some pictures
* ran the debugger
* figured out how to set the breakpoint so you got your failure case
* read 20-50 times more code than they wrote
* asked peers to look at a problem
* got asked by peers to look at a problem
* surfed the web looking for answers
* and so on...

Notice that writing code into the machine occupies only a very small portion of this list.  That’s because the hard part of programming is the thinking, not the typing.  Everything else on that list is about thinking, not typing.

* TDD increases your production because it serves as a thinking-aid. It limits back-tracking and gold-plating and it reduces code-study and debugging.

* Pairing increases your production for the very same reason.  Two developers together don’t type as fast as two developers separately, but we don’t care: the  bottleneck is the thinking.
