#! /usr/bin/python -B

import re


def add_joined(ll, l):
    return add_if_not_empty(' '.join(ll), l)


def add_if_not_empty(s, l):
    s = s.strip()
    if len(s) > 0:
        l.append(s)
    return l


def can_ignore(tag, ignoring=[]):
    for e in ignoring:
        if re.match('^%s$' % e, tag):
            return True
    return False


def split_first(string, keyword, ignoring=[]):
    match = re.search(keyword, string)
    if match:
        pre = string[:match.start()]
        tag = match.group(0)
        end = string[match.end():]
        if can_ignore(tag, ignoring):
            parsed_end = split_first(end, keyword, ignoring)
            if len(parsed_end) == 3:
                parsed_end[0] = '%s%s%s' % (pre, tag, parsed_end[0])
                return parsed_end
        else:
            return [pre, tag, end]
    return [string]


def parse(line_iterable):
    omit = ['</?br/?>']
    opens = '<[^>/]+>'
    tags = '<[^>]+>'
    current = []
    stack = []
    line = []
    rem = ''
    while True:
        try:
            if len(rem) == 0:
                rem = line_iterable.next().strip()
            with_tag = split_first(rem, tags, omit)
            if len(with_tag) == 3:
                (pre, tag, end) = with_tag
                add_if_not_empty(pre, line)
                add_joined(line, current)
                line = []
                rem = end
                if re.match(opens, tag):
                    stack.append(current)
                    current = [tag]
                else:
                    current.append(tag)
                    previous = stack.pop()
                    previous.append(current)
                    current = previous
            else:
                add_if_not_empty(rem, line)
                rem = ''
        except StopIteration:
            add_joined(line, current)
            break
    return current
