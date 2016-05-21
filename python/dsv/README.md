### Delimeter-Separated Values

This is a generic implementation of DSV formats such as `csv` and `tsv`. Generally these formats follow the same parsing rules and differ in the _enclosing_, _escaping_ and _separating_ characters. Hence, class `DSV` lets you configure those characters and use the same logic to escape strings, parse lines and join lists into the adequate format.

Let us take the `csv` format as an example. This format uses `,` as the separating character, `"` as the enclosing character and `"` also as the escaping character:

```python
>>> csv = DSV(',', '"', '"')
>>> par = csv.parse('normal,"quoted, text","escapes ""this"" word"')
>>> print par
['normal', 'quoted, text', 'escapes "this" word']
```
