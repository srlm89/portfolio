INCLUDE=-include /usr/include/errno.h
EXTRAFLAGS:= -m32 -Wall -Werror -pedantic --std=c99 -ggdb
NASMEXTRAFLAGS:=-g -f elf32

EXTS=asm c
SRCDIR=./src
OBJDIR=./bin
BSTDIR=asm/binary-search-tree
DLLDIR=asm/double-linked-list

SRC=$(foreach EXT,$(EXTS),$(shell find $(SRCDIR) -name "*.$(EXT)"))
OBJ=$(subst $(SRCDIR),$(OBJDIR),$(filter %.o,$(foreach EXT, $(EXTS), $(SRC:%.$(EXT)=%.o))))

BST=bintree
BSTSRC=$(filter-out $(SRCDIR)/$(DLLDIR)/%,$(SRC))
BSTOBJ=$(filter-out $(OBJDIR)/$(DLLDIR)/%,$(OBJ))

DLL=doublelinked
DLLSRC=$(filter-out $(SRCDIR)/$(BSTDIR)/%,$(SRC))
DLLOBJ=$(filter-out $(OBJDIR)/$(BSTDIR)/%,$(OBJ))

.PHONY: all clean

all: $(BST) $(DLL)

$(BST): $(OBJDIR) $(BSTOBJ)
	gcc $(INCLUDE) -o $@ $(EXTRAFLAGS) $(BSTOBJ)

$(DLL): $(OBJDIR) $(DLLOBJ)
	gcc $(INCLUDE) -o $@ $(EXTRAFLAGS) $(DLLOBJ)

$(OBJDIR)/%.o: $(SRCDIR)/%.asm
	nasm $(NASMEXTRAFLAGS) -o $@ $<

$(OBJDIR)/%.o: $(SRCDIR)/%.c
	gcc -c $(EXTRAFLAGS) -o $@ $<

$(OBJDIR):
	@echo "Creating $@ folder structure..."
	$(foreach OBJF,$(OBJ),$(shell mkdir -p `dirname $(OBJF)`))

clean:
	rm -f $(BST)
	rm -f $(DLL)
	rm -rf $(OBJDIR)

