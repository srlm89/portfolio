INCLUDE=-include /usr/include/errno.h
EXTRAFLAGS:= -m32 -Wall -Werror -pedantic --std=c99 -ggdb
NASMEXTRAFLAGS:=-g -f elf32

SRCDIR=./src
OBJDIR=./bin
OPS=$(SRCDIR)/operations.c

BST=bintree
BSTDIR=binary-search-tree
BSTSRC=$(wildcard $(SRCDIR)/$(BSTDIR)/*.asm) $(wildcard $(SRCDIR)/*.asm)
BSTOBJS=$(subst $(SRCDIR),$(OBJDIR),$(BSTSRC:%.asm=%.o))

DLL=doublelinked
DLLDIR=double-linked-list
DLLSRC=$(wildcard $(SRCDIR)/$(DLLDIR)/*.asm) $(wildcard $(SRCDIR)/*.asm)
DLLOBJS=$(subst $(SRCDIR),$(OBJDIR),$(DLLSRC:%.asm=%.o))

.PHONY: all clean

all: $(BSTDIR) $(BST) $(DLLDIR) $(DLL)

$(BST): $(OPS) $(BSTOBJS) $(BSTSRC)
	gcc $(INCLUDE) -o $@ $(EXTRAFLAGS) $< $(BSTOBJS)

$(DLL): $(OPS) $(DLLOBJS) $(DLLSRC)
	gcc $(INCLUDE) -o $@ $(EXTRAFLAGS) $< $(DLLOBJS)

$(OBJDIR)/%.o: $(SRCDIR)/%.asm
	nasm $(NASMEXTRAFLAGS) -o $@ $<

$(OBJDIR)/%.o: $(SRCDIR)/%.c
	gcc -c $(EXTRAFLAGS) -o $@ $<

$(DLLDIR):
	mkdir -p $(OBJDIR)/$(DLLDIR)

$(BSTDIR):
	mkdir -p $(OBJDIR)/$(BSTDIR)

clean:
	rm -f $(BSTOBJS) $(BST)
	rm -f $(DLLOBJS) $(DLL)
	rm -rf $(OBJDIR)

