CC=gcc
ASM=nasm
CFLAGS=-ggdb -Wall -std=c99 -pedantic
ASMFLAGS=-felf32 -g -F dwarf
OPENCV_LIB=`pkg-config --cflags --libs opencv` -lm

EXTS=asm c
SRCDIR=./src
OBJDIR=./bin
BIN=$(OBJDIR)/choose

SRC=$(foreach EXT, $(EXTS), $(shell find $(SRCDIR) -name "*.$(EXT)"))
OBJ=$(subst $(SRCDIR),$(OBJDIR),$(filter %.o,$(foreach EXT, $(EXTS), $(SRC:%.$(EXT)=%.o))))

.PHONY: all clean

all: $(BIN)

$(BIN): $(OBJDIR) $(OBJ)
	$(CC) $(CFLAGS) $(OBJ) -o $@ $(OPENCV_LIB)

$(OBJDIR)/%.o: $(SRCDIR)/%.c
	$(CC) $(CFLAGS) -c -o $@ $<

$(OBJDIR)/%.o: $(SRCDIR)/%.asm
	$(ASM) $(ASMFLAGS) -o $@ $<

$(OBJDIR):
	@echo "Creating $@ folder structure..."
	$(foreach OBJF,$(OBJ),$(shell mkdir -p `dirname $(OBJF)`))

clean:
	rm -rf $(OBJDIR)
	rm -f $(BIN)

