
class DSV:

    def __init__(self, separator=',', encloser='"', escaper='"'):
        self._sep = separator
        self._enc = encloser
        self._esc = escaper

    def escape(self, string):
        if self._sep in string or self._enc in string:
            if self._enc in string:
                string = string.replace(self._enc, self._esc + self._enc)
            string = self._enc + string + self._enc
        return string

    def join(self, iterable):
        escaped = map(self.escape, iterable)
        return self._sep.join(escaped)

    def parse_file(self, file_path):
        with open(file_path, 'r') as f:
            lines = []
            for line in f:
                parsed = self.parse(line.strip())
                lines.append(parsed)
            return lines

    def parse(self, line):
        parts = []
        i = 0
        while i < len(line):
            j = self.end_of_column(line, i)
            part = self.extracted(line, i, j)
            parts.append(part)
            i = j + 1
        if i > 0 and i == len(line):
            parts.append('')
        return parts

    def end_of_column(self, line, i):
        j = i
        n = len(line)
        enclosed = False
        while j < n:
            c = line[j]
            escaper = c == self._esc
            encloser = c == self._enc
            separator = c == self._sep
            next_encloser = j+1 < n and line[j+1] == self._enc
            if enclosed:
                if escaper and next_encloser:
                    j += 1
                elif encloser:
                    enclosed = False
            else:
                if encloser:
                    if next_encloser:
                        j += 1
                    else:
                        enclosed = True
                elif separator:
                    break
            j += 1
        return j

    def extracted(self, line, i, j):
        part = line[i:j]
        if part.startswith(self._enc):
            part = part[1:-1]
            part = part.replace(self._esc + self._enc, self._enc)
        return part

tsv = DSV('\t', '"', '"')
csv = DSV(',', '"', '"')
