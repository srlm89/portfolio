
def dec2hexa(n):
    codes = []
    num = n
    while num != 0 or len(codes) == 0:
        code = hexa_code_for(num % 16)
        codes.append(code)
        num /= 16
    rev = [n for n in reversed(codes)]
    return '0x%s' % ''.join(rev)


def hexa_code_for(n):
    if n > 9:
        num = ord('A') + n - 10
        return chr(num)
    else:
        return str(n)