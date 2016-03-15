### HTML Parser

An _ad hoc_ implementation of a basic HTML Parser. It parses the structure of an HTML document into a list structure. For instance, the following document:

```html
<html lang="en">
<head>
    <title>Title</title>
</head>
<body></body>
</html>
```

Produces the following python list:

```python
[['<html lang="en">',
    ['<head>', ['<title>', 'Title', '</title>'], '</head>'],
    ['<body>', '</body>'],
'</html>']]
```
