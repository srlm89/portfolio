import unittest


class StringSortTest(unittest.TestCase):

    az = [chr(e) for e in range(ord('a'), 1 + ord('z'))]
    acgt = ['a', 'c', 'g', 't']

    def test_sorts_many_repetitions(self):
        self.sort_in_actg(['catac'],
                          ['catac'])
        self.sort_in_actg(['tcat'],
                          ['tcat'])
        self.sort_in_actg(['gatgggccat'],
                          ['gatgggccat'])
        self.sort_in_actg(['gcgcagaggc'],
                          ['gcgcagaggc'])
        self.sort_in_actg(['ggccatacgt', 'ttcccctc'],
                          ['ggccatacgt', 'ttcccctc'])
        self.sort_in_actg(['cggca', 'ggtta'],
                          ['ggtta', 'cggca'])
        self.sort_in_actg(['ccgac', 'gcttccgaga'],
                          ['ccgac', 'gcttccgaga'])
        self.sort_in_actg(['accg', 'gtgtttctgc'],
                          ['gtgtttctgc', 'accg'])
        self.sort_in_actg(['a', 'ggacaaa', 'tcgagc'],
                          ['ggacaaa', 'tcgagc', 'a'])
        self.sort_in_actg(['agtcaa', 'gggacgag', 'tgagtaatt'],
                          ['gggacgag', 'agtcaa', 'tgagtaatt'])
        self.sort_in_actg(['agtagctat', 'cag', 'gcgccg'],
                          ['gcgccg', 'cag', 'agtagctat'])
        self.sort_in_actg(['gctgtc', 'tagtcgca', 'tccggaa'],
                          ['gctgtc', 'tccggaa', 'tagtcgca'])
        self.sort_in_actg(['aacgagctat', 'ag', 'g'],
                          ['aacgagctat', 'g', 'ag'])
        self.sort_in_actg(['c', 'gaatga', 'tctagg'],
                          ['c', 'tctagg', 'gaatga'])
        self.sort_in_actg(['c', 'gtatcact', 'tagtatc'],
                          ['tagtatc', 'c', 'gtatcact'])
        self.sort_in_actg(['a', 'cggt', 'tag', 'tg'],
                          ['tg', 'tag', 'a', 'cggt'])
        self.sort_in_actg(['cgcaaac', 'ctagtgcgg', 'gcatc', 'tgc'],
                          ['tgc', 'ctagtgcgg', 'gcatc', 'cgcaaac'])
        self.sort_in_actg(['agact', 'atgg', 'cataatt', 'tgcctt'],
                          ['cataatt', 'atgg', 'tgcctt', 'agact'])
        self.sort_in_actg(['aatctaactg', 'catgtccgg', 'gagggtca', 'tacttgct', 'tgctccatg'],
                          ['tacttgct', 'gagggtca', 'catgtccgg', 'aatctaactg', 'tgctccatg'])
        self.sort_in_actg(['ca', 'ggggccgcc', 'tagca', 'tt', 'ttggtctatt'],
                          ['tt', 'ttggtctatt', 'ggggccgcc', 'tagca', 'ca'])
        self.sort_in_actg(['a', 'aaaagactta', 'agggatc', 'gacgtc', 'gctgcaca', 'ttcaggag'],
                          ['gctgcaca', 'ttcaggag', 'a', 'gacgtc', 'agggatc', 'aaaagactta'])
        self.sort_in_actg(['at', 'c', 'ca', 'cgcag', 'gccgaagtct', 'tgtgtacac'],
                          ['c', 'at', 'gccgaagtct', 'ca', 'cgcag', 'tgtgtacac'])
        self.sort_in_actg(['a', 'ccac', 'cccgccgaa', 't', 'tcccgctcg', 'tgaag'],
                          ['tgaag', 'tcccgctcg', 't', 'cccgccgaa', 'ccac', 'a'])
        self.sort_in_actg(['a', 'aatacctttg', 'agtcgagata', 'ggatgacct', 'gttt', 'tg'],
                          ['gttt', 'a', 'agtcgagata', 'ggatgacct', 'tg', 'aatacctttg'])
        self.sort_in_actg(['aaaa', 'aacaact', 'cc', 'cgtga', 'ggat', 'ttagtgtg'],
                          ['cgtga', 'ggat', 'aacaact', 'cc', 'aaaa', 'ttagtgtg'])
        self.sort_in_actg(['ac', 'accgtc', 'agccccac', 'ca', 'cgggcatc', 'g', 'tattg'],
                          ['g', 'agccccac', 'ca', 'tattg', 'cgggcatc', 'ac', 'accgtc'])
        self.sort_in_actg(['acaccta', 'accaacat', 'ag', 'agcttg', 'aggtcag', 'caga', 'tgaccaatc'],
                          ['acaccta', 'caga', 'tgaccaatc', 'agcttg', 'ag', 'accaacat', 'aggtcag'])
        self.sort_in_actg(['acaca', 'agac', 'ca', 'ctc', 'g', 'gtaattcgca', 'tgtcgtc'],
                          ['gtaattcgca', 'acaca', 'g', 'tgtcgtc', 'ca', 'ctc', 'agac'])
        self.sort_in_actg(['a', 'actaact', 'ccagaga', 'ccct', 'gggc', 'ggggcttg', 'tggtct'],
                          ['actaact', 'tggtct', 'gggc', 'ccagaga', 'ggggcttg', 'ccct', 'a'])
        self.sort_in_actg(['atccggt', 'ca', 'cagca', 'ccccact', 'tgctgg', 'tgg', 'ttgaat'],
                          ['ca', 'tgg', 'cagca', 'ttgaat', 'tgctgg', 'ccccact', 'atccggt'])
        self.sort_in_actg(['a', 'accacgct', 'attt', 'cagacagtg', 'ctcga', 'gg', 'ttcaaggtcg'],
                          ['accacgct', 'gg', 'attt', 'cagacagtg', 'ttcaaggtcg', 'a', 'ctcga'])
        self.sort_in_actg(['aatgcta', 'agacc', 'agcggtg', 'cacggtaa', 'ctcga', 't', 'tgaagacg', 'tttaa'],
                          ['agacc', 'aatgcta', 'tgaagacg', 'tttaa', 'ctcga', 'agcggtg', 'cacggtaa', 't'])
        self.sort_in_actg(['actactac', 'agcagt', 'atgtct', 'ctc', 'gagc', 'gcg', 'tcg', 'tggat'],
                          ['gcg', 'agcagt', 'actactac', 'tcg', 'tggat', 'atgtct', 'ctc', 'gagc'])
        self.sort_in_actg(['agatgttgac', 'cgagc', 'ctgtctgta', 'gcaaatag', 'gcactccac', 'tatacctta', 'tgca', 'tt'],
                          ['cgagc', 'tgca', 'tatacctta', 'tt', 'gcaaatag', 'agatgttgac', 'gcactccac', 'ctgtctgta'])
        self.sort_in_actg(['aa', 'acttc', 'ag', 'agtaaagt', 'gctatga', 'tcatggc', 'ttacctac', 'ttggtg'],
                          ['ag', 'ttacctac', 'aa', 'tcatggc', 'ttggtg', 'gctatga', 'agtaaagt', 'acttc'])
        self.sort_in_actg(['acggtta', 'agcccg', 'atagtt', 'cccagt', 'cgtcggcgtc', 'ctgtaactgt', 'ggggatgc', 'tagcgtagc'],
                          ['cccagt', 'agcccg', 'acggtta', 'tagcgtagc', 'ctgtaactgt', 'ggggatgc', 'cgtcggcgtc', 'atagtt'])
        self.sort_in_actg(['aaaag', 'ag', 'c', 'ctg', 'ctgct', 'ggaaattc', 'tccgtgggtc', 'tcg'],
                          ['ctgct', 'tccgtgggtc', 'c', 'ggaaattc', 'tcg', 'ag', 'aaaag', 'ctg'])
        self.sort_in_actg(['aaatcgcta', 'accctgctga', 'agatatccat', 'aggg', 'cccccg', 'gcggtgcc', 'gggt', 'tagat', 'tgttaaagt'],
                          ['cccccg', 'accctgctga', 'agatatccat', 'tagat', 'aggg', 'gggt', 'tgttaaagt', 'aaatcgcta', 'gcggtgcc'])
        self.sort_in_actg(['aa', 'acacg', 'accccac', 'acgggga', 'agaa', 'gtcg', 'gttgg', 'tccctc', 'tta'],
                          ['aa', 'agaa', 'gtcg', 'tccctc', 'acacg', 'tta', 'acgggga', 'gttgg', 'accccac'])
        self.sort_in_actg(['acg', 'ctagcctcc', 'cttc', 'ga', 'gagtcagc', 'gcacactc', 'gt', 'tacatg', 'tctcc'],
                          ['gt', 'ga', 'tctcc', 'tacatg', 'cttc', 'gagtcagc', 'acg', 'ctagcctcc', 'gcacactc'])
        self.sort_in_actg(['aaagg', 'ccgaaccc', 'ccttaac', 'cg', 'cgatattc', 'cgttgcgg', 'ct', 't', 'tatgctgtta'],
                          ['t', 'aaagg', 'ccttaac', 'ct', 'cgttgcgg', 'cg', 'tatgctgtta', 'cgatattc', 'ccgaaccc'])
        self.sort_in_actg(['acggac', 'aggca', 'ccggaact', 'cctgt', 'ggacgtca', 'gggccgctat', 'gttgcgatgg', 'tccgaat', 'tctaaactcg', 'tttcgtg'],
                          ['aggca', 'cctgt', 'gggccgctat', 'ccggaact', 'acggac', 'tccgaat', 'ggacgtca', 'tctaaactcg', 'gttgcgatgg', 'tttcgtg'])
        self.sort_in_actg(['agggtgac', 'atcggaa', 'attacgc', 'atttatgt', 'caacttt', 'cagtccaat', 'cataaacc', 'cg', 'ga', 'tgtaatcg'],
                          ['cg', 'attacgc', 'ga', 'cagtccaat', 'tgtaatcg', 'caacttt', 'cataaacc', 'agggtgac', 'atcggaa', 'atttatgt'])
        self.sort_in_actg(['acgcc', 'acttgg', 'at', 'cc', 'ta', 'taggt', 'tcact', 'tgc', 'tgg', 'ttgtcgcca'],
                          ['tgg', 'tgc', 'acgcc', 'at', 'ttgtcgcca', 'ta', 'acttgg', 'taggt', 'tcact', 'cc'])
        self.sort_in_actg(['a', 'agggttta', 'cacgc', 'cct', 'cctctt', 'cttgaacg', 'tattta', 'tc', 'tctatac', 'tg'],
                          ['cttgaacg', 'tc', 'tattta', 'cct', 'a', 'tctatac', 'cacgc', 'tg', 'agggttta', 'cctctt'])
        self.sort_in_actg(['acgtaa', 'ag', 'cggtc', 'g', 'gaatgaa', 'gggtagaga', 'gtatc', 'gtgcatacaa', 'taacaat', 'taggta'],
                          ['ag', 'gaatgaa', 'gtatc', 'taacaat', 'gtgcatacaa', 'acgtaa', 'gggtagaga', 'g', 'taggta', 'cggtc'])
        self.sort_in_actg(['aattcttgc', 'acaactagt', 'agagg', 'ccagt', 'g', 'gca', 'tat', 'tca', 'tccgatcga', 'tgttcaatt'],
                          ['agagg', 'aattcttgc', 'tca', 'tccgatcga', 'acaactagt', 'tat', 'g', 'gca', 'ccagt', 'tgttcaatt'])
        self.sort_in_actg(['acagca', 'acgtata', 'ccc', 'ccga', 'cgaata', 'cgctag', 'gat', 'gatttctc', 't', 't', 'tttagaaaa'],
                          ['t', 'acagca', 'gat', 'cgaata', 't', 'acgtata', 'ccc', 'ccga', 'tttagaaaa', 'cgctag', 'gatttctc'])
        self.sort_in_actg(['cctagt', 'g', 'gc', 'gca', 'tatccgggca', 'tatgc', 'tccgt', 'tcg', 'tggat', 'tta', 'ttag'],
                          ['tatgc', 'cctagt', 'tcg', 'ttag', 'g', 'tatccgggca', 'tggat', 'tccgt', 'gc', 'gca', 'tta'])
        self.sort_in_actg(['a', 'ac', 'c', 'catgtta', 'cc', 'cgatacg', 'cgg', 'gactcgtagc', 'gc', 'gggc', 'tc'],
                          ['a', 'ac', 'cgg', 'tc', 'c', 'gc', 'catgtta', 'cc', 'gggc', 'gactcgtagc', 'cgatacg'])
        self.sort_in_actg(['aa', 'acactaca', 'cag', 'cc', 'ccgagtcaga', 'cgcatcta', 'gaagata', 'gacaa', 'gact', 'gc', 'tacggaat', 'tcaccatggt'],
                          ['gacaa', 'gc', 'tcaccatggt', 'gaagata', 'acactaca', 'aa', 'cgcatcta', 'gact', 'tacggaat', 'cc', 'cag', 'ccgagtcaga'])
        self.sort_in_actg(['accac', 'aggcccgg', 'cacgtac', 'cagaac', 'cggtctaaag', 'gacg', 'gtaccggt', 'tca', 'tccca', 'tg', 'tgcgga', 'ttgtt'],
                          ['ttgtt', 'cagaac', 'cggtctaaag', 'cacgtac', 'accac', 'aggcccgg', 'tg', 'tca', 'gtaccggt', 'tgcgga', 'tccca', 'gacg'])
        self.sort_in_actg(['aac', 'aatcgga', 'accccaca', 'cg', 'cgacggagg', 'ct', 'g', 't', 'tcctggc', 'tgg', 'tt', 'ttgc'],
                          ['g', 'tt', 'cgacggagg', 'accccaca', 'cg', 'aac', 'tgg', 'ct', 'tcctggc', 'aatcgga', 't', 'ttgc'])
        self.sort_in_actg(['aca', 'aggcaca', 'atcgctcc', 'caaccaaaa', 'cggaata', 'cggacctt', 'cgtg', 'gcaaca', 'ggcgc', 'gtcccagggc', 'tcacccact', 'tg'],
                          ['tg', 'gtcccagggc', 'aggcaca', 'caaccaaaa', 'cggaata', 'gcaaca', 'ggcgc', 'atcgctcc', 'tcacccact', 'cgtg', 'cggacctt', 'aca'])
        self.sort_in_actg(['aaatacgct', 'accgg', 'agcagatca', 'aggctg', 'caatc', 'cacaccccg', 'ccagt', 'cgcgg', 'gg', 'gtg', 'ta', 'tcgccatact', 'ttat'],
                          ['aaatacgct', 'tcgccatact', 'ccagt', 'ttat', 'aggctg', 'cgcgg', 'ta', 'gg', 'agcagatca', 'cacaccccg', 'gtg', 'accgg', 'caatc'])
        self.sort_in_actg(['actatgcg', 'ataacac', 'cc', 'ctactga', 'gaa', 'gaaag', 'ggcctacta', 'gtccttcc', 'gtcgatactc', 't', 't', 'tacatgg', 'tagcatcc'],
                          ['tacatgg', 'gaaag', 't', 'gtccttcc', 'actatgcg', 'ataacac', 'ctactga', 'gaa', 'tagcatcc', 't', 'ggcctacta', 'gtcgatactc', 'cc'])
        self.sort_in_actg(['a', 'aaaggtc', 'aaagtgt', 'aaatccg', 'gc', 'gcatta', 'gggg', 'ta', 'tcgtt', 'tgaatacacc', 'tgtgaatca', 'tt', 'ttgtg'],
                          ['gc', 'gcatta', 'gggg', 'tt', 'ttgtg', 'tgtgaatca', 'tcgtt', 'tgaatacacc', 'aaaggtc', 'aaatccg', 'a', 'aaagtgt', 'ta'])
        self.sort_in_actg(['a', 'acc', 'actggcggaa', 'agtacg', 'c', 'cacagtccaa', 'cattggatta', 'cttacc', 'ggt', 'tagg', 'tagg', 'tctaca', 'tctt'],
                          ['acc', 'cacagtccaa', 'tagg', 'tagg', 'agtacg', 'tctaca', 'a', 'cattggatta', 'c', 'tctt', 'actggcggaa', 'ggt', 'cttacc'])
        self.sort_in_actg(['a', 'acaggtcc', 'atcc', 'cattga', 'cggagat', 'ctca', 'ctccgg', 'gaaagc', 'gagagcca', 'gcactggg', 'gtgaa', 'tcaact', 'tcttc'],
                          ['gcactggg', 'tcaact', 'cattga', 'ctca', 'gaaagc', 'a', 'gtgaa', 'cggagat', 'tcttc', 'ctccgg', 'atcc', 'acaggtcc', 'gagagcca'])
        self.sort_in_actg(['aa', 'aacca', 'atggac', 'gaaacccta', 'gactag', 'ggtcc', 'tagct', 'tgc', 'tgcgtag', 'tggagctag', 'ttcagta', 'ttcgtga', 'tttaccata'],
                          ['tagct', 'gaaacccta', 'ttcgtga', 'tggagctag', 'tttaccata', 'aacca', 'ggtcc', 'tgc', 'gactag', 'ttcagta', 'aa', 'atggac', 'tgcgtag'])
        self.sort_in_actg(['acgtc', 'agattcatg', 'attgtggttc', 'cacgc', 'ccaaatt', 'ccggc', 'cct', 'cgcaagcc', 't', 't', 'tactacccca', 'tat', 'tatattc'],
                          ['t', 'agattcatg', 'tatattc', 'ccaaatt', 'cgcaagcc', 'tactacccca', 'attgtggttc', 'acgtc', 't', 'tat', 'cct', 'ccggc', 'cacgc'])
        self.sort_in_actg(['aa', 'aat', 'at', 'atagt', 'ccgccgcat', 'ctggg', 'cttga', 'gcga', 'gctgcaca', 'ta', 'tcc', 'tcccgtttat', 'ttcag'],
                          ['atagt', 'ctggg', 'gctgcaca', 'cttga', 'ta', 'aa', 'aat', 'at', 'ccgccgcat', 'tcc', 'tcccgtttat', 'gcga', 'ttcag'])
        self.sort_in_actg(['aaaccagtt', 'atagtc', 'ccgtttct', 'cctta', 'cggccag', 'gaactgccaa', 'gcatggga', 'ggacatagg', 't', 'tagacgtat', 'tggg', 'tgttggat', 'tt'],
                          ['tagacgtat', 'atagtc', 'ccgtttct', 'tgttggat', 'gaactgccaa', 'tggg', 't', 'cggccag', 'ggacatagg', 'gcatggga', 'tt', 'aaaccagtt', 'cctta'])
        self.sort_in_actg(['aa', 'aacccagggt', 'aaccg', 'aattggcctg', 'cacattg', 'cccga', 'cttatcc', 'gctcg', 'ggatcgtc', 'gtaagaa', 't', 'taa', 'tcataga'],
                          ['cacattg', 'taa', 'gtaagaa', 'aaccg', 'aacccagggt', 't', 'aa', 'gctcg', 'ggatcgtc', 'cttatcc', 'cccga', 'tcataga', 'aattggcctg'])
        self.sort_in_actg(['a', 'aacatggcg', 'aca', 'c', 'cgccaacc', 'g', 'g', 'gagc', 'tactttaa', 'tcggc', 'tgt', 'tgtctc', 'ttcccta', 'tttgtcacc'],
                          ['tactttaa', 'aacatggcg', 'aca', 'c', 'tgtctc', 'gagc', 'ttcccta', 'tcggc', 'tgt', 'a', 'g', 'g', 'tttgtcacc', 'cgccaacc'])
        self.sort_in_actg(['a', 'a', 'aactgc', 'agctcggtg', 'ccc', 'cct', 'cggccgagt', 'cttaacg', 'ggaagtcgag', 'ggcagtccc', 'ggg', 'gt', 'gtg', 'tcccgct'],
                          ['ccc', 'gt', 'ggcagtccc', 'ggg', 'ggaagtcgag', 'gtg', 'cct', 'cggccgagt', 'cttaacg', 'aactgc', 'tcccgct', 'a', 'agctcggtg', 'a'])
        self.sort_in_actg(['a', 'aacc', 'aatac', 'acttcgatg', 'atccc', 'cgcacct', 'g', 'g', 'gcctgc', 'gtaga', 'gttcggaa', 'tacct', 'tcagaac', 'tcgctcg'],
                          ['gcctgc', 'a', 'aatac', 'acttcgatg', 'tcagaac', 'cgcacct', 'g', 'gtaga', 'gttcggaa', 'atccc', 'aacc', 'g', 'tcgctcg', 'tacct'])
        self.sort_in_actg(['aat', 'aca', 'acgattc', 'actccctc', 'actg', 'ag', 'agaattta', 'attaagt', 'c', 'cttta', 'gacatattt', 'gagagg', 'ggc', 'ggtagagggc'],
                          ['ag', 'gacatattt', 'ggc', 'acgattc', 'actccctc', 'aca', 'actg', 'attaagt', 'c', 'agaattta', 'cttta', 'gagagg', 'aat', 'ggtagagggc'])
        self.sort_in_actg(['aa', 'acaga', 'atccacc', 'caatcacaa', 'cgtcag', 'ctaaag', 'gc', 'gccatc', 'gccgt', 'ggacct', 'gt', 'tcagta', 'tcggata', 'tttcgtgg'],
                          ['atccacc', 'gt', 'aa', 'ctaaag', 'ggacct', 'cgtcag', 'gc', 'tcagta', 'caatcacaa', 'gccgt', 'tttcgtgg', 'tcggata', 'acaga', 'gccatc'])
        self.sort_in_actg(['aaaccgg', 'agccgaa', 'agtcgcaatc', 'atttcggat', 'ccttggcacg', 'g', 'gatggt', 'gtgg', 'gttatc', 'gttcgcct', 't', 'tagc', 'ttagtc', 'tttaa'],
                          ['aaaccgg', 'gtgg', 'gatggt', 'atttcggat', 'gttatc', 'g', 'tagc', 'ccttggcacg', 't', 'agtcgcaatc', 'gttcgcct', 'ttagtc', 'tttaa', 'agccgaa'])
        self.sort_in_actg(['acgtggta', 'ag', 'c', 'ccc', 'ccggcataaa', 'ct', 'ctatttg', 'gg', 'ggcta', 'gtagaa', 'gtgtc', 'tcag', 'ttcg', 'ttctt'],
                          ['ttctt', 'ct', 'ttcg', 'ccggcataaa', 'gg', 'acgtggta', 'ctatttg', 'gtagaa', 'ggcta', 'gtgtc', 'tcag', 'ag', 'ccc', 'c'])
        self.sort_in_actg(['ag', 'agg', 'atag', 'caggaggg', 'ctgggcacc', 'gagct', 'gcg', 'ggggctaga', 'gt', 'gtgatgcta', 'tatgtgc', 'tg', 'tgccaccga', 'ttgggttga', 'tttacgtg'],
                          ['caggaggg', 'gagct', 'tttacgtg', 'ttgggttga', 'gtgatgcta', 'tg', 'gt', 'atag', 'gcg', 'tatgtgc', 'agg', 'ggggctaga', 'tgccaccga', 'ctgggcacc', 'ag'])
        self.sort_in_actg(['ac', 'c', 'cactcccc', 'cactgg', 'cccttcaggc', 'ccga', 'cctcgggt', 'cg', 'cgaaagt', 'g', 'gagccaact', 'gtctga', 'tc', 'tcagg', 'tg'],
                          ['cgaaagt', 'g', 'cg', 'cctcgggt', 'cactgg', 'ac', 'tcagg', 'cactcccc', 'tg', 'cccttcaggc', 'gagccaact', 'c', 'ccga', 'gtctga', 'tc'])
        self.sort_in_actg(['aaaagagt', 'acggc', 'actccgtt', 'caaca', 'catggc', 'cc', 'cg', 'cgtcaa', 'g', 'gacagt', 'taat', 'tctacga', 'tt', 'ttattgct', 'ttgcc'],
                          ['cgtcaa', 'cc', 'ttattgct', 'tctacga', 'taat', 'ttgcc', 'g', 'acggc', 'tt', 'catggc', 'gacagt', 'cg', 'aaaagagt', 'actccgtt', 'caaca'])
        self.sort_in_actg(['ag', 'atcgga', 'cagatac', 'cattgttt', 'gaagtgg', 'gat', 'gcaaccag', 'gcg', 'ggagattc', 'gtccc', 'gtg', 'tatct', 'tc', 'tcagata', 'tct'],
                          ['atcgga', 'tatct', 'tc', 'tct', 'tcagata', 'gcaaccag', 'gcg', 'gtg', 'cattgttt', 'ag', 'gaagtgg', 'gtccc', 'cagatac', 'gat', 'ggagattc'])
        self.sort_in_actg(['a', 'aatttgcc', 'actgtctacg', 'agttat', 'atctctgt', 'ccgatc', 'cggggaatat', 'cgttg', 'ct', 'ctggtat', 'gggctgct', 'gtggtgt', 'taa', 'taccggg', 'ttg'],
                          ['ctggtat', 'gggctgct', 'agttat', 'gtggtgt', 'taa', 'ttg', 'a', 'ccgatc', 'cggggaatat', 'atctctgt', 'actgtctacg', 'taccggg', 'aatttgcc', 'ct', 'cgttg'])
        self.sort_in_actg(['acaatac', 'agcgcctt', 'agtgagacg', 'ataccccca', 'cacgagttgt', 'ccatgcgag', 'ccatggaggc', 'gc', 'gc', 'ggc', 'ggtcac', 'tc', 'tg', 'ttgaacg', 'ttgttaatt'],
                          ['ccatgcgag', 'cacgagttgt', 'ggc', 'agcgcctt', 'agtgagacg', 'ggtcac', 'gc', 'ttgaacg', 'ataccccca', 'ttgttaatt', 'tg', 'tc', 'acaatac', 'ccatggaggc', 'gc'])
        self.sort_in_actg(['a', 'ag', 'cacatgtatt', 'ccaatata', 'ccatcccaag', 'g', 'ga', 'gattgcgc', 'gc', 'gtgcccgttg', 'ta', 'taga', 'tgc', 'tggctccgc', 'ttgtgg'],
                          ['ag', 'tggctccgc', 'gc', 'tgc', 'gtgcccgttg', 'gattgcgc', 'cacatgtatt', 'a', 'ccaatata', 'ccatcccaag', 'ga', 'ttgtgg', 'g', 'taga', 'ta'])
        self.sort_in_actg(['aa', 'ag', 'cagccattga', 'ccgagt', 'ccgcctg', 'cgccg', 'gc', 'ggcccagt', 'gtaattagtc', 'gtctt', 'gttctattg', 'taag', 'tccc', 'tct', 'tctgttc', 'tga'],
                          ['ggcccagt', 'ccgagt', 'tctgttc', 'tccc', 'cgccg', 'cagccattga', 'tga', 'gtaattagtc', 'taag', 'gc', 'tct', 'aa', 'ccgcctg', 'gttctattg', 'gtctt', 'ag'])
        self.sort_in_actg(['aatggacg', 'acggtttcg', 'atc', 'ctgttac', 'gaacagctgg', 'gc', 'gcg', 'gctact', 'gt', 'gtca', 'gttgcgtgt', 't', 't', 't', 'tg', 'ttatcgccta'],
                          ['gctact', 'gtca', 'gc', 'ttatcgccta', 't', 'atc', 't', 'acggtttcg', 't', 'gaacagctgg', 'gcg', 'gttgcgtgt', 'gt', 'ctgttac', 'tg', 'aatggacg'])
        self.sort_in_actg(['aataatttg', 'ac', 'acgct', 'agagctagc', 'atccttagg', 'cccgg', 'cgacaggc', 'cgcccgc', 'cgttgag', 'gca', 'tcgggatag', 'tcttcta', 'tgacca', 'tggcgataca', 'ttagc', 'tttaccctac'],
                          ['agagctagc', 'cgcccgc', 'ttagc', 'aataatttg', 'cccgg', 'gca', 'atccttagg', 'tgacca', 'acgct', 'ac', 'tcttcta', 'tcgggatag', 'cgttgag', 'cgacaggc', 'tttaccctac', 'tggcgataca'])
        self.sort_in_actg(['a', 'aatcggg', 'agaca', 'agtaacgccg', 'agtgagagg', 'agtttaa', 'cg', 'cgtctc', 'ctt', 'cttgaaa', 'g', 'gcaaagactg', 'gt', 'taag', 'tagccc', 'tcag'],
                          ['gcaaagactg', 'ctt', 'cgtctc', 'agtaacgccg', 'tcag', 'cg', 'gt', 'tagccc', 'agtttaa', 'g', 'aatcggg', 'cttgaaa', 'taag', 'a', 'agtgagagg', 'agaca'])
        self.sort_in_actg(['aaaacataa', 'aac', 'aagagtgt', 'act', 'actc', 'at', 'attaaccg', 'ca', 'cat', 'cgctc', 'gagtat', 'gatcgatcag', 'ggtggcc', 'gttgt', 'tccc', 'tgtact', 'tgttac'],
                          ['gttgt', 'at', 'ca', 'tgtact', 'attaaccg', 'cgctc', 'tccc', 'tgttac', 'cat', 'gatcgatcag', 'gagtat', 'ggtggcc', 'act', 'aac', 'aagagtgt', 'aaaacataa', 'actc'])
        self.sort_in_actg(['a', 'aacttcgt', 'agcctc', 'c', 'cat', 'ccagtc', 'cg', 'g', 'gatt', 'gct', 'gctgg', 'gcttta', 't', 'tatcttaa', 'tgactgaaaa', 'tgtc', 'ttatcc'],
                          ['g', 'gcttta', 'tgactgaaaa', 'agcctc', 't', 'a', 'tgtc', 'ttatcc', 'gatt', 'ccagtc', 'cat', 'aacttcgt', 'cg', 'c', 'gct', 'tatcttaa', 'gctgg'])
        self.sort_in_actg(['a', 'actcaatgg', 'agaata', 'c', 'cccagtgctt', 'cgat', 'ctgactac', 'ctgtg', 'gccgactaaa', 'gggc', 'ggtacct', 'ta', 'taaa', 'tatgtttc', 'tcttggagta', 'tggaaagaa', 'tgtgaa'],
                          ['taaa', 'gccgactaaa', 'ggtacct', 'tatgtttc', 'actcaatgg', 'ctgtg', 'agaata', 'gggc', 'ctgactac', 'tgtgaa', 'tcttggagta', 'tggaaagaa', 'cgat', 'ta', 'c', 'a', 'cccagtgctt'])
        self.sort_in_actg(['aa', 'aatcccca', 'acgctca', 'actt', 'agggactcga', 'c', 'caaat', 'cat', 'cattg', 'ccaggagtc', 'ctgacttt', 'gcatgctata', 'gtggc', 't', 'tatacttca', 'tcaggga', 'tct'],
                          ['agggactcga', 'aa', 'c', 'aatcccca', 'ccaggagtc', 'tct', 'gcatgctata', 'tatacttca', 'cat', 'gtggc', 'caaat', 't', 'cattg', 'tcaggga', 'ctgacttt', 'actt', 'acgctca'])
        self.sort_in_actg(['c', 'cacatc', 'ctctagc', 'ctg', 'ctggct', 'g', 'gatt', 'gc', 'gccgaga', 'gctcaca', 'ggaggaaca', 'ggtggcca', 'gttgtttag', 'tcggtgtaa', 'tcgt', 'ttat', 'ttgaac'],
                          ['tcggtgtaa', 'ggtggcca', 'gctcaca', 'gttgtttag', 'ctggct', 'g', 'ttgaac', 'ttat', 'ggaggaaca', 'gc', 'cacatc', 'c', 'gccgaga', 'tcgt', 'ctctagc', 'gatt', 'ctg'])
        self.sort_in_actg(['a', 'aaatg', 'aacttc', 'act', 'agctgcct', 'c', 'cacgtatc', 'g', 'gc', 'gtcctagac', 't', 'ta', 'taatttgc', 'tcc', 'tgggtgccta', 'ttct', 'ttttaagga'],
                          ['gc', 'agctgcct', 'ta', 'act', 't', 'ttct', 'cacgtatc', 'c', 'taatttgc', 'aaatg', 'gtcctagac', 'tgggtgccta', 'aacttc', 'a', 'g', 'ttttaagga', 'tcc'])
        self.sort_in_actg(['aaaaccg', 'aaacat', 'acaatcaa', 'ag', 'attacttcc', 'c', 'c', 'ca', 'cagaatg', 'cccttaat', 'ccgccga', 'cga', 'gaattcatag', 'gcgggagaa', 'gctatttgaa', 'gggccagtgc', 'ta', 'tgcaact'],
                          ['c', 'gggccagtgc', 'aaaaccg', 'aaacat', 'ccgccga', 'attacttcc', 'acaatcaa', 'c', 'ca', 'gaattcatag', 'tgcaact', 'cccttaat', 'cga', 'ag', 'cagaatg', 'gcgggagaa', 'ta', 'gctatttgaa'])
        self.sort_in_actg(['a', 'agc', 'agtcacaa', 'atta', 'cg', 'ct', 'ctag', 'gcagggacga', 'gctttcac', 'gtaagatgcc', 'gtatgt', 't', 't', 'taggtcggct', 'tat', 'tcccca', 'tcgtggc', 'tg'],
                          ['gcagggacga', 'gtatgt', 'ctag', 'agc', 'tcgtggc', 'agtcacaa', 'tcccca', 'gctttcac', 'tg', 'taggtcggct', 'tat', 'a', 'ct', 'gtaagatgcc', 't', 't', 'atta', 'cg'])
        self.sort_in_actg(['aattta', 'accgattagc', 'agcag', 'agtatcgtt', 'cacaa', 'ccc', 'cgcgtttcg', 'cgtc', 'ctgctgcga', 'cttgtg', 'gacgtt', 'gatttc', 'gc', 'gg', 'ggccacagat', 'gtaccgaa', 'gttagt', 'tagtcgtg', 'ttcggg'],
                          ['ggccacagat', 'cgtc', 'agcag', 'gtaccgaa', 'cacaa', 'gatttc', 'ttcggg', 'gacgtt', 'accgattagc', 'gc', 'tagtcgtg', 'gg', 'aattta', 'cttgtg', 'ctgctgcga', 'agtatcgtt', 'gttagt', 'cgcgtttcg', 'ccc'])
        self.sort_in_actg(['a', 'aacgctctat', 'acg', 'atactcccta', 'cactagagag', 'catcat', 'ccct', 'ccctgtac', 'cg', 'cgt', 'ctgggtt', 'ga', 'gagctt', 'gcgagccg', 'gtgag', 'gttcaa', 'tagc', 'tgcgt', 'tttacgaatt'],
                          ['gtgag', 'ctgggtt', 'tagc', 'a', 'gagctt', 'ga', 'acg', 'atactcccta', 'gcgagccg', 'tttacgaatt', 'cg', 'gttcaa', 'cgt', 'catcat', 'aacgctctat', 'cactagagag', 'ccct', 'tgcgt', 'ccctgtac'])
        self.sort_in_actg(['aaag', 'aacttt', 'aagcca', 'acggtact', 'acgtatgaa', 'agctcacat', 'aggata', 'atttacta', 'cctat', 'ctat', 'cttatttt', 'g', 'gaggtc', 'gcgtcttac', 'gctaataac', 'ggtt', 'gtt', 't', 'ta'],
                          ['ctat', 'gaggtc', 'aggata', 'cttatttt', 'gtt', 'g', 'ta', 'gctaataac', 'agctcacat', 'acggtact', 'gcgtcttac', 'aagcca', 'aacttt', 'cctat', 'acgtatgaa', 'ggtt', 'aaag', 'atttacta', 't'])
        self.sort_in_actg(['aaatgccg', 'aacgg', 'accctagt', 'agcagaag', 'c', 'c', 'ccattcccgg', 'cctggaacc', 'cgtgg', 'gagaagcac', 'gagcccatg', 'gccgtataa', 'gcgtctat', 'gt', 'taagcc', 'tccga', 'tgtgct', 'ttag', 'tttgaaaca'],
                          ['cctggaacc', 'aacgg', 'accctagt', 'tccga', 'gcgtctat', 'gagcccatg', 'aaatgccg', 'ttag', 'agcagaag', 'taagcc', 'c', 'tttgaaaca', 'ccattcccgg', 'tgtgct', 'cgtgg', 'c', 'gagaagcac', 'gt', 'gccgtataa'])
        self.sort_in_actg(['a', 'aagac', 'aatcagcag', 'aatcgtaaaa', 'acat', 'actcgtcag', 'catt', 'ctt', 'g', 'gacagaggaa', 'gccgaaatct', 'gcta', 'ggtgcttga', 't', 'taagg', 'tcagttctt', 'tctgggttgc', 'tgatatacgt', 'ttcgt'],
                          ['catt', 'ggtgcttga', 'ctt', 'actcgtcag', 'aatcgtaaaa', 'aagac', 'tctgggttgc', 'ttcgt', 'taagg', 'tcagttctt', 'aatcagcag', 'acat', 'tgatatacgt', 'gcta', 'gacagaggaa', 'g', 't', 'gccgaaatct', 'a'])
        self.sort_in_actg(['aaacc', 'accttagcg', 'acgcggtgaa', 'agga', 'aggtga', 'agtggac', 'atgagat', 'attt', 'cagtggagg', 'catcc', 'cattgcgc', 'cccga', 'gctcaggga', 'gttcgcggc', 'taact', 'tga', 'tgccacggg', 'tgccc', 'tgtc'],
                          ['acgcggtgaa', 'taact', 'cccga', 'cattgcgc', 'atgagat', 'agga', 'accttagcg', 'aaacc', 'aggtga', 'cagtggagg', 'tgccc', 'tgccacggg', 'gctcaggga', 'gttcgcggc', 'tgtc', 'tga', 'agtggac', 'attt', 'catcc'])
        self.sort_in_actg(['acagaatgtt', 'acccta', 'aga', 'agacagt', 'c', 'c', 'cctcgcaaga', 'cgaagataag', 'cgaagccaag', 'ctacgtaca', 'gag', 'gctgcata', 'taaagac', 'taatctaatc', 'tacgg', 'tgca', 'tgtcgg', 'ttaggc', 'ttcgaaagt', 'tttgcagac'],
                          ['aga', 'ctacgtaca', 'cgaagccaag', 'c', 'tgtcgg', 'ttaggc', 'acagaatgtt', 'cctcgcaaga', 'taatctaatc', 'ttcgaaagt', 'tgca', 'gag', 'agacagt', 'acccta', 'cgaagataag', 'c', 'tacgg', 'tttgcagac', 'gctgcata', 'taaagac'])
        self.sort_in_actg(['aatt', 'acagttg', 'aga', 'cacaat', 'cga', 'cgac', 'cgcttcga', 'ctaat', 'ctcgc', 'ctgtagggc', 'gccacgtc', 'gcgtc', 'ggagac', 'ggat', 'tgag', 'tggtcggct', 'tgttcag', 'ttgaaa', 'tttc', 'tttgcgatgg'],
                          ['ctcgc', 'ggat', 'tttgcgatgg', 'ttgaaa', 'ctgtagggc', 'cgcttcga', 'gcgtc', 'ctaat', 'tgag', 'cga', 'tttc', 'tggtcggct', 'tgttcag', 'aga', 'ggagac', 'acagttg', 'cgac', 'aatt', 'gccacgtc', 'cacaat'])
        self.sort_in_actg(['a', 'aaccacta', 'aagtgcca', 'aattat', 'acgcat', 'acgg', 'cctgtac', 'cg', 'cgc', 'g', 'ga', 'gaaacct', 'gaagg', 'gagcacca', 'gaggtc', 'ggattc', 'gt', 't', 'tatgt', 'tcaaa'],
                          ['acgcat', 'gaaacct', 'a', 'cctgtac', 'tatgt', 'acgg', 'aagtgcca', 'cgc', 'aattat', 'cg', 'aaccacta', 'tcaaa', 't', 'gt', 'gaggtc', 'ga', 'g', 'ggattc', 'gaagg', 'gagcacca'])
        self.sort_in_actg(['a', 'aa', 'agaacctcgt', 'agagca', 'agg', 'atat', 'attctcgc', 'caacttg', 'ct', 'ctac', 'ctccc', 'cttattt', 'gaa', 'gcttatcct', 'gga', 'ggaca', 'gggcact', 't', 'tacgg', 'tgct'],
                          ['attctcgc', 'gcttatcct', 'aa', 'atat', 'gggcact', 't', 'tacgg', 'ct', 'agg', 'cttattt', 'agagca', 'ctac', 'caacttg', 'ggaca', 'gaa', 'agaacctcgt', 'ctccc', 'tgct', 'gga', 'a'])

    def test_sorts(self):
        self.sort_in_az([], [])
        self.sort_in_az(['b'],
                        ['b'])
        self.sort_in_az(['baovuf'],
                        ['baovuf'])
        self.sort_in_az(['tokff'],
                        ['tokff'])
        self.sort_in_az(['lav'],
                        ['lav'])
        self.sort_in_az(['dffmrnbvp'],
                        ['dffmrnbvp'])
        self.sort_in_az(['criynx'],
                        ['criynx'])
        self.sort_in_az(['jkr', 'q'],
                        ['jkr', 'q'])
        self.sort_in_az(['bzvfnjes', 'msaqpnw'],
                        ['msaqpnw', 'bzvfnjes'])
        self.sort_in_az(['ehufkvsilh', 'vs'],
                        ['vs', 'ehufkvsilh'])
        self.sort_in_az(['dkdytnfrht', 'ntbgwpt'],
                        ['ntbgwpt', 'dkdytnfrht'])
        self.sort_in_az(['igggwnoj', 'krgfijr'],
                        ['krgfijr', 'igggwnoj'])
        self.sort_in_az(['eeeynnsvb', 'lcnf', 'pgzpikxac'],
                        ['lcnf', 'pgzpikxac', 'eeeynnsvb'])
        self.sort_in_az(['aubvhgm', 'rdmeah', 'spyx'],
                        ['rdmeah', 'aubvhgm', 'spyx'])
        self.sort_in_az(['ewgily', 'm', 'pre'],
                        ['pre', 'ewgily', 'm'])
        self.sort_in_az(['tvsgw', 'xslshaxe', 'xxyz'],
                        ['xxyz', 'tvsgw', 'xslshaxe'])
        self.sort_in_az(['aoryjldwl', 'ja', 'w'],
                        ['aoryjldwl', 'w', 'ja'])
        self.sort_in_az(['ehd', 'fxjung', 'lvoiokxkdv', 'snxmgmts'],
                        ['ehd', 'fxjung', 'snxmgmts', 'lvoiokxkdv'])
        self.sort_in_az(['intixov', 'n', 'qhj', 'vjtwuw'],
                        ['qhj', 'n', 'intixov', 'vjtwuw'])
        self.sort_in_az(['nsawyacz', 'ssszikoimb', 't', 'wfyikdm'],
                        ['ssszikoimb', 'wfyikdm', 'nsawyacz', 't'])
        self.sort_in_az(['idmbsottca', 'jefxcsqp', 'jpusi', 'kfzd'],
                        ['jefxcsqp', 'jpusi', 'idmbsottca', 'kfzd'])
        self.sort_in_az(['dtc', 'iuqnnbvun', 'oxnjdb', 'piqmnb', 'u'],
                        ['dtc', 'piqmnb', 'u', 'iuqnnbvun', 'oxnjdb'])
        self.sort_in_az(['dnqy', 'fjhvy', 'hlmhvdvx', 'ojjdgmdo', 'wtqdnd'],
                        ['fjhvy', 'wtqdnd', 'ojjdgmdo', 'dnqy', 'hlmhvdvx'])
        self.sort_in_az(['cnpghhzzip', 'df', 'qa', 'rskkfi', 'ykbgnyusz'],
                        ['qa', 'ykbgnyusz', 'rskkfi', 'df', 'cnpghhzzip'])
        self.sort_in_az(['fnlsxlv', 'hjeuyow', 'hrd', 'lcxccajm', 'lebo'],
                        ['hjeuyow', 'hrd', 'lebo', 'lcxccajm', 'fnlsxlv'])
        self.sort_in_az(['kous', 'qmydcbtl', 'twvk', 'umzdoqckg', 'zidgvgng'],
                        ['twvk', 'umzdoqckg', 'qmydcbtl', 'zidgvgng', 'kous'])
        self.sort_in_az(['cofpg', 'qixsdhxh', 'rcyfzqh', 'xkhtfny', 'zgyhelegjm'],
                        ['zgyhelegjm', 'qixsdhxh', 'rcyfzqh', 'cofpg', 'xkhtfny'])
        self.sort_in_az(['gqwztz', 'hxkgcw', 'ivksiizmn', 'ptdu', 'xfgfjuzvt'],
                        ['xfgfjuzvt', 'gqwztz', 'hxkgcw', 'ptdu', 'ivksiizmn'])
        self.sort_in_az(['ahepccjvk', 'bsqonr', 'iw', 'l', 'mlryza', 'yv'],
                        ['mlryza', 'bsqonr', 'iw', 'ahepccjvk', 'l', 'yv'])
        self.sort_in_az(['iwqwp', 'o', 'qkoe', 'vwtxez', 'wpodlq', 'xukahqayz'],
                        ['qkoe', 'wpodlq', 'xukahqayz', 'iwqwp', 'o', 'vwtxez'])
        self.sort_in_az(['auczaosind', 'btontv', 'hpromogahk', 'ljgf', 'loryqmoz', 'vpkqwdyw'],
                        ['vpkqwdyw', 'btontv', 'ljgf', 'auczaosind', 'loryqmoz', 'hpromogahk'])
        self.sort_in_az(['ggereextxf', 'mnm', 'onikvau', 'raxmqzccy', 's', 't'],
                        ['t', 'mnm', 's', 'onikvau', 'raxmqzccy', 'ggereextxf'])
        self.sort_in_az(['cagzosk', 'cdot', 'h', 'j', 'jcgtwr', 'mvmdvvx'],
                        ['j', 'jcgtwr', 'h', 'cdot', 'cagzosk', 'mvmdvvx'])
        self.sort_in_az(['bz', 'lelpwtey', 'owphux', 'qi', 'rzdhol', 'x'],
                        ['x', 'rzdhol', 'owphux', 'qi', 'lelpwtey', 'bz'])
        self.sort_in_az(['aut', 'lni', 'maesshwkxj', 'redbsvqlx', 'sannsewyl', 'vxg', 'zdvuvxr'],
                        ['lni', 'maesshwkxj', 'sannsewyl', 'vxg', 'redbsvqlx', 'aut', 'zdvuvxr'])
        self.sort_in_az(['hpbrotzqx', 'jqzziy', 'mhydlmrw', 'nqfs', 'quajdbr', 'vaphopr', 'vsfdn'],
                        ['hpbrotzqx', 'jqzziy', 'mhydlmrw', 'vsfdn', 'vaphopr', 'nqfs', 'quajdbr'])
        self.sort_in_az(['czqbonoy', 'dgpvh', 'dxdi', 'jz', 'rza', 'vnuwlv', 'z'],
                        ['z', 'vnuwlv', 'dxdi', 'jz', 'czqbonoy', 'rza', 'dgpvh'])
        self.sort_in_az(['aflpz', 'llhbpkgwub', 'mbuspohc', 'rtntnxqv', 'sfsglbfurs', 'uffno', 'xtj'],
                        ['xtj', 'uffno', 'rtntnxqv', 'sfsglbfurs', 'aflpz', 'mbuspohc', 'llhbpkgwub'])
        self.sort_in_az(['bherlibbo', 'ck', 'io', 'lehrzohrqb', 'tlp', 'vytpyzamsd', 'wzfs'],
                        ['vytpyzamsd', 'ck', 'lehrzohrqb', 'bherlibbo', 'tlp', 'wzfs', 'io'])
        self.sort_in_az(['apnyvxcshb', 'bdtdsidbw', 'bzu', 'hx', 'kqmhyhume', 'lelj', 'yxopieql'],
                        ['lelj', 'yxopieql', 'bzu', 'hx', 'bdtdsidbw', 'kqmhyhume', 'apnyvxcshb'])
        self.sort_in_az(['ce', 'hrc', 'imuvvkszu', 'qjuakgogma', 'tv', 'wqquvysyt', 'wthrekw'],
                        ['tv', 'ce', 'qjuakgogma', 'wthrekw', 'wqquvysyt', 'imuvvkszu', 'hrc'])
        self.sort_in_az(['ipyw', 'iqagprarld', 'lowip', 'mpzpgafzol', 'rpfvqm', 'sytvjrvhds', 'vyohv'],
                        ['vyohv', 'sytvjrvhds', 'rpfvqm', 'iqagprarld', 'ipyw', 'mpzpgafzol', 'lowip'])
        self.sort_in_az(['ddmyus', 'fufejl', 'ihudzeqp', 'rghe', 'rzmfmju', 'tgpydfsv', 'uj', 'wxoyp'],
                        ['fufejl', 'rzmfmju', 'uj', 'wxoyp', 'ihudzeqp', 'tgpydfsv', 'rghe', 'ddmyus'])
        self.sort_in_az(['bjibf', 'brzvzvgt', 'djbgn', 'dzxlquawr', 'fvzzmws', 'hdemxkodo', 'hspaaw', 'ql'],
                        ['djbgn', 'ql', 'brzvzvgt', 'dzxlquawr', 'fvzzmws', 'hspaaw', 'bjibf', 'hdemxkodo'])
        self.sort_in_az(['dfyj', 'do', 'kphkjtgahr', 'ksea', 'nbl', 'ojxquotit', 'py', 'yrhlemxlpq'],
                        ['dfyj', 'do', 'py', 'nbl', 'ojxquotit', 'ksea', 'yrhlemxlpq', 'kphkjtgahr'])
        self.sort_in_az(['arxyizfvor', 'dmtpxgj', 'hylqnexn', 'kj', 'pewhez', 't', 'wqefybt', 'yhwpuxctcm'],
                        ['wqefybt', 'kj', 'pewhez', 'hylqnexn', 'arxyizfvor', 't', 'dmtpxgj', 'yhwpuxctcm'])
        self.sort_in_az(['j', 'jacd', 'jsbqmdn', 'qtjsjqe', 'r', 'rscdvytaoz', 'vdkxsbylh', 'vjjjzh', 'ydllhkpt'],
                        ['jsbqmdn', 'vdkxsbylh', 'vjjjzh', 'j', 'ydllhkpt', 'jacd', 'rscdvytaoz', 'qtjsjqe', 'r'])
        self.sort_in_az(['bpjpoagk', 'bz', 'cxmkggb', 'ddzvyicaws', 'ffmv', 'jbwqiob', 'qzkhrwrjdg', 'ujxqnzg', 'yadxmuwhgt', 'ztdk'],
                        ['qzkhrwrjdg', 'jbwqiob', 'bpjpoagk', 'yadxmuwhgt', 'ujxqnzg', 'ddzvyicaws', 'ffmv', 'cxmkggb', 'ztdk', 'bz'])
        self.sort_in_az(['ax', 'ifc', 'kjhmf', 'kzccy', 'matxwv', 'pjtn', 'qjun', 'ru', 'wv', 'z'],
                        ['kjhmf', 'ru', 'qjun', 'ax', 'kzccy', 'matxwv', 'ifc', 'pjtn', 'z', 'wv'])
        self.sort_in_az(['h', 'idojmxv', 'qh', 't', 't', 'tjexl', 'x', 'xrj', 'xtyeqcwskq', 'ya'],
                        ['idojmxv', 'x', 't', 'qh', 'ya', 'tjexl', 't', 'h', 'xtyeqcwskq', 'xrj'])
        self.sort_in_az(['aaa', 'ahowp', 'fhnlsigr', 'fvzwj', 'hniipha', 'immtov', 'kyuawa', 'wbfgjrrz', 'zjunrb', 'zqggkg'],
                        ['fhnlsigr', 'fvzwj', 'wbfgjrrz', 'immtov', 'hniipha', 'ahowp', 'kyuawa', 'zqggkg', 'zjunrb', 'aaa'])
        self.sort_in_az(['apjmfl', 'dnmunygit', 'g', 'ptysuvgo', 'xmdrc', 'xn', 'y', 'yjpmeoyic', 'yvssmcabo', 'z'],
                        ['apjmfl', 'z', 'xn', 'g', 'y', 'xmdrc', 'dnmunygit', 'yvssmcabo', 'yjpmeoyic', 'ptysuvgo'])
        self.sort_in_az(['ekvlf', 'exjvk', 'fqh', 'hj', 'kl', 'lodsukpwpu', 'lvumr', 'vpfjwuam', 'vzehumsil', 'xix'],
                        ['vzehumsil', 'xix', 'lodsukpwpu', 'fqh', 'vpfjwuam', 'lvumr', 'kl', 'ekvlf', 'hj', 'exjvk'])
        self.sort_in_az(['ctlskzan', 'gffpvnfky', 'jnghkfrv', 'kzn', 'oo', 's', 'ucf', 'uh', 'uweladj', 'uyqotawvg', 'wuevbpgxzz'],
                        ['ucf', 'oo', 'wuevbpgxzz', 'ctlskzan', 'uyqotawvg', 'uh', 'uweladj', 'jnghkfrv', 'gffpvnfky', 's', 'kzn'])
        self.sort_in_az(['b', 'cwcaic', 'dbxzyd', 'dvc', 'grrqxxtuo', 'lmx', 'nvnxi', 'thprxoyuxb', 'vv', 'vvm', 'yxeedjh'],
                        ['dvc', 'lmx', 'vv', 'dbxzyd', 'nvnxi', 'cwcaic', 'vvm', 'grrqxxtuo', 'yxeedjh', 'thprxoyuxb', 'b'])
        self.sort_in_az(['dmom', 'fqfaaodviv', 'frwpdzxwaq', 'jeidnqaxyx', 'lkeqodt', 'wzxadhvgq', 'xdznbysgzi', 'xzs', 'yv', 'zbrxirxy', 'zmviwy'],
                        ['xdznbysgzi', 'jeidnqaxyx', 'zbrxirxy', 'zmviwy', 'xzs', 'dmom', 'lkeqodt', 'frwpdzxwaq', 'yv', 'wzxadhvgq', 'fqfaaodviv'])
        self.sort_in_az(['bkuzfe', 'diphv', 'f', 'gwra', 'ikbhtqxxdn', 'jipju', 'kb', 'kov', 'qynj', 'tuwcw', 'vits'],
                        ['kov', 'jipju', 'gwra', 'bkuzfe', 'diphv', 'kb', 'f', 'ikbhtqxxdn', 'vits', 'qynj', 'tuwcw'])
        self.sort_in_az(['befjvik', 'cdenwqgee', 'fxflyeual', 'hknnjgem', 'hxgwaged', 'jcmdhasxri', 'ofylcefib', 'qo', 'sbjdrm', 'uqtsc', 'veiixtoz', 'xg'],
                        ['hknnjgem', 'jcmdhasxri', 'qo', 'sbjdrm', 'befjvik', 'ofylcefib', 'xg', 'cdenwqgee', 'uqtsc', 'veiixtoz', 'fxflyeual', 'hxgwaged'])
        self.sort_in_az(['diyeozu', 'fblzmhg', 'kfjwtadzln', 'kphmmfv', 'olcafo', 'qjacxsepy', 'tgav', 'ysnrwlio', 'ze', 'zi', 'zlhdqq', 'zri'],
                        ['zi', 'kphmmfv', 'olcafo', 'tgav', 'fblzmhg', 'diyeozu', 'kfjwtadzln', 'ze', 'ysnrwlio', 'qjacxsepy', 'zlhdqq', 'zri'])
        self.sort_in_az(['agibzppcj', 'hzvfxljm', 'kyxapfkdam', 'okgrehsb', 'onukukzile', 'rtmja', 'sctjpsughd', 't', 'th', 'ujiyrz', 'vixnahw', 'wpp', 'zsnbog'],
                        ['zsnbog', 'rtmja', 'kyxapfkdam', 'th', 'vixnahw', 'okgrehsb', 'sctjpsughd', 'onukukzile', 'agibzppcj', 't', 'ujiyrz', 'hzvfxljm', 'wpp'])
        self.sort_in_az(['a', 'a', 'bzfjaov', 'ck', 'cpzcshsqjz', 'etdcsu', 'gzswu', 'j', 'ny', 'q', 'rciibmyt', 'tqxjcqwc', 'ubf'],
                        ['ubf', 'etdcsu', 'a', 'q', 'j', 'tqxjcqwc', 'gzswu', 'ck', 'a', 'rciibmyt', 'ny', 'bzfjaov', 'cpzcshsqjz'])
        self.sort_in_az(['bh', 'cj', 'cn', 'kdynkikbn', 'kjklz', 'lwglf', 'nlupefykz', 'ogqjgoykt', 'pawstppb', 'phgxiznr', 'rwmqrzeo', 'w', 'wjggmlidbp'],
                        ['w', 'phgxiznr', 'cj', 'pawstppb', 'lwglf', 'kjklz', 'nlupefykz', 'kdynkikbn', 'ogqjgoykt', 'cn', 'bh', 'rwmqrzeo', 'wjggmlidbp'])
        self.sort_in_az(['au', 'by', 'cwyjhdu', 'dembr', 'dugtksa', 'fskoweh', 'jjfkbr', 'kkld', 'ralbrnzojz', 'uhj', 'wx', 'xpehgh', 'yfktqjnrx'],
                        ['dugtksa', 'yfktqjnrx', 'wx', 'uhj', 'xpehgh', 'jjfkbr', 'au', 'by', 'fskoweh', 'kkld', 'dembr', 'cwyjhdu', 'ralbrnzojz'])
        self.sort_in_az(['bdygevu', 'efytjpugse', 'gzsqgpvz', 'idsracmaxd', 'ik', 'ns', 'qtiralk', 'rt', 's', 'sptro', 'sxkxl', 'u', 'x'],
                        ['idsracmaxd', 'bdygevu', 'efytjpugse', 'gzsqgpvz', 'ik', 'u', 'x', 'sxkxl', 'qtiralk', 'ns', 's', 'rt', 'sptro'])
        self.sort_in_az(['b', 'bmpvhf', 'bmxi', 'cza', 'gljue', 'heqibqwssk', 'hpctz', 'pqkeb', 'sfzczul', 'upumfgn', 'vbx', 'vshyli', 'xueibegzv'],
                        ['bmpvhf', 'vbx', 'pqkeb', 'heqibqwssk', 'upumfgn', 'xueibegzv', 'b', 'hpctz', 'sfzczul', 'vshyli', 'gljue', 'cza', 'bmxi'])
        self.sort_in_az(['bdwftis', 'c', 'gnvmn', 'hoxkd', 'k', 'lfnaom', 'mffgibxg', 'nskpqqxwz', 'pbmhofqmra', 'wq', 'wzwyt', 'yaaxtv', 'z'],
                        ['wzwyt', 'bdwftis', 'pbmhofqmra', 'nskpqqxwz', 'k', 'lfnaom', 'yaaxtv', 'wq', 'mffgibxg', 'gnvmn', 'c', 'z', 'hoxkd'])
        self.sort_in_az(['anvbiad', 'cea', 'feblxhyhjg', 'h', 'ktd', 'nsg', 'qham', 's', 'so', 'sxuphrn', 'tqifyi', 'xy', 'zccmp'],
                        ['so', 'qham', 'anvbiad', 'nsg', 'feblxhyhjg', 'tqifyi', 'ktd', 'h', 'sxuphrn', 's', 'cea', 'xy', 'zccmp'])
        self.sort_in_az(['a', 'qoflweh', 'qpu', 'rjfpotck', 'shvfiipr', 't', 'uyuadu', 'w', 'worlwgo', 'wzwzt', 'xkncaf', 'ybiptmhkh', 'yv', 'zwhtqnikt'],
                        ['t', 'ybiptmhkh', 'wzwzt', 'a', 'qoflweh', 'zwhtqnikt', 'worlwgo', 'shvfiipr', 'xkncaf', 'rjfpotck', 'qpu', 'yv', 'uyuadu', 'w'])
        self.sort_in_az(['biecsjmmx', 'cd', 'dn', 'dshsara', 'duhnyfvuxy', 'i', 'iusrxe', 'lzlsdtnv', 'po', 'pq', 'ptaloqskq', 'qwdib', 'zi', 'zrfhfk'],
                        ['dn', 'cd', 'pq', 'lzlsdtnv', 'i', 'zrfhfk', 'qwdib', 'duhnyfvuxy', 'ptaloqskq', 'zi', 'biecsjmmx', 'iusrxe', 'po', 'dshsara'])
        self.sort_in_az(['ajhe', 'bsfyttibq', 'ckwnutqjy', 'gmua', 'iyx', 'jbcfjizbd', 'ksfhltyaxt', 'omc', 's', 'uiaj', 'viqp', 'vmandzvz', 'wni', 'ywqagnf'],
                        ['ajhe', 's', 'viqp', 'jbcfjizbd', 'wni', 'ywqagnf', 'iyx', 'uiaj', 'omc', 'vmandzvz', 'ckwnutqjy', 'ksfhltyaxt', 'bsfyttibq', 'gmua'])
        self.sort_in_az(['aisxpsxks', 'btpe', 'ddol', 'eqa', 'fpyo', 'gbnuz', 'hku', 'l', 'nrpyogufr', 'nxomkbnadj', 'tixn', 'uhz', 'vb', 'yfbgcaszqh', 'zvfbrbqa'],
                        ['uhz', 'l', 'eqa', 'vb', 'zvfbrbqa', 'gbnuz', 'yfbgcaszqh', 'fpyo', 'hku', 'nrpyogufr', 'tixn', 'nxomkbnadj', 'ddol', 'btpe', 'aisxpsxks'])
        self.sort_in_az(['aezkdgur', 'd', 'fnm', 'kgxbcekqu', 'koreqo', 'mq', 'oddzgnj', 'otkxzl', 'pghg', 'rlhwkrmnrz', 'udksyebdzr', 'udlefxoa', 'wst', 'xyshigz', 'ydbkbilmmu'],
                        ['d', 'kgxbcekqu', 'otkxzl', 'oddzgnj', 'mq', 'udksyebdzr', 'pghg', 'xyshigz', 'udlefxoa', 'fnm', 'aezkdgur', 'ydbkbilmmu', 'wst', 'koreqo', 'rlhwkrmnrz'])
        self.sort_in_az(['bopktjxr', 'ep', 'irlojgxtlk', 'nc', 'nxe', 'oxsiuarhi', 'ozvykjo', 'p', 'pkjfuzsk', 'szk', 'ttsllhx', 'uenfq', 'wckfbhf', 'yjzrkcgdyk', 'zkbpsnxl'],
                        ['szk', 'zkbpsnxl', 'wckfbhf', 'bopktjxr', 'ep', 'nc', 'nxe', 'uenfq', 'oxsiuarhi', 'irlojgxtlk', 'p', 'pkjfuzsk', 'ttsllhx', 'yjzrkcgdyk', 'ozvykjo'])
        self.sort_in_az(['aiq', 'amblsqayu', 'azsdccvjq', 'beukty', 'dgdmax', 'hsghf', 'hszhqfj', 'huk', 'kqw', 'nciln', 'nphyuzhzu', 'omlwhnt', 'rgdcbiyaw', 'uobecajgqb', 'wkzzq'],
                        ['nphyuzhzu', 'hszhqfj', 'beukty', 'dgdmax', 'amblsqayu', 'rgdcbiyaw', 'aiq', 'kqw', 'huk', 'azsdccvjq', 'omlwhnt', 'hsghf', 'wkzzq', 'uobecajgqb', 'nciln'])
        self.sort_in_az(['crvnx', 'ctndvvvmq', 'eqceblk', 'eszyii', 'ixhqfr', 'jnzch', 'nb', 'nfscdbc', 'nwdsozigg', 'prpymdhktd', 'pyi', 'rvq', 'sxn', 'xhs', 'z'],
                        ['rvq', 'crvnx', 'z', 'nb', 'jnzch', 'eszyii', 'prpymdhktd', 'pyi', 'nwdsozigg', 'ctndvvvmq', 'xhs', 'sxn', 'nfscdbc', 'ixhqfr', 'eqceblk'])
        self.sort_in_az(['cykgqd', 'fkjwpvbfif', 'guo', 'ixhedvcl', 'jpppdvo', 'lpjast', 'oayzae', 'qdmrnukg', 'rlq', 'tlmj', 'trv', 'yrd', 'yvazzwy', 'z', 'zrhnakd'],
                        ['rlq', 'ixhedvcl', 'z', 'zrhnakd', 'oayzae', 'lpjast', 'qdmrnukg', 'guo', 'tlmj', 'yvazzwy', 'fkjwpvbfif', 'trv', 'jpppdvo', 'yrd', 'cykgqd'])
        self.sort_in_az(['amwpejxthj', 'df', 'hkoejyu', 'hskdjnlnx', 'jxzbsfyx', 'k', 'mzpdslklj', 'nr', 'qsyjvs', 'rxsuffrb', 'safjecqifh', 'so', 'xhxvqsf', 'ydrzn', 'zhctiqq'],
                        ['k', 'zhctiqq', 'qsyjvs', 'hkoejyu', 'jxzbsfyx', 'nr', 'rxsuffrb', 'hskdjnlnx', 'amwpejxthj', 'so', 'ydrzn', 'mzpdslklj', 'xhxvqsf', 'df', 'safjecqifh'])
        self.sort_in_az(['cl', 'cmlktgsz', 'd', 'ewbzl', 'jv', 'mrkowzuncg', 'mycogatvs', 'qbgj', 'scdove', 'tjdojovjh', 'u', 'whzvoxjcy', 'xrp', 'xyha', 'yhjvw', 'zslqqkzrv'],
                        ['cmlktgsz', 'u', 'cl', 'qbgj', 'zslqqkzrv', 'jv', 'mrkowzuncg', 'xyha', 'yhjvw', 'tjdojovjh', 'd', 'scdove', 'whzvoxjcy', 'ewbzl', 'xrp', 'mycogatvs'])
        self.sort_in_az(['dk', 'elmz', 'fyitqyjypr', 'ibuynef', 'igc', 'jxorbkw', 'nhbfsdk', 'nhhicbwyfr', 'no', 'pk', 'rh', 'rik', 'rwm', 'sqoqhzluji', 'uvimrwu', 'zcpqdyms'],
                        ['pk', 'rh', 'elmz', 'no', 'igc', 'rik', 'sqoqhzluji', 'rwm', 'zcpqdyms', 'fyitqyjypr', 'dk', 'ibuynef', 'jxorbkw', 'nhbfsdk', 'nhhicbwyfr', 'uvimrwu'])
        self.sort_in_az(['av', 'befew', 'bp', 'euu', 'fmhmnkpobb', 'gonjof', 'inxjugkoi', 'kksglqtktb', 'lrwa', 'nmm', 'o', 'q', 'snktvzipyl', 'txfjf', 'uflc', 'umd'],
                        ['o', 'nmm', 'gonjof', 'q', 'snktvzipyl', 'txfjf', 'inxjugkoi', 'lrwa', 'bp', 'umd', 'uflc', 'befew', 'kksglqtktb', 'fmhmnkpobb', 'av', 'euu'])
        self.sort_in_az(['aihek', 'bax', 'bsh', 'dd', 'ftil', 'fyzt', 'gkik', 'hsbbfcsgm', 'nbaid', 'rm', 'speyta', 't', 'tdcotchxec', 'ut', 'vbp', 'wqvlmnxsm', 'x'],
                        ['gkik', 'bax', 't', 'ftil', 'tdcotchxec', 'nbaid', 'vbp', 'wqvlmnxsm', 'bsh', 'ut', 'speyta', 'rm', 'fyzt', 'aihek', 'hsbbfcsgm', 'dd', 'x'])
        self.sort_in_az(['a', 'aon', 'cimw', 'egp', 'fzfdkelqp', 'g', 'g', 'hoqusdr', 'ioplehov', 'kz', 'mtvnufm', 'nsvx', 'smabm', 'tywyss', 'vc', 'yvrgec', 'zlyonubq'],
                        ['mtvnufm', 'zlyonubq', 'ioplehov', 'g', 'smabm', 'vc', 'aon', 'kz', 'g', 'a', 'egp', 'tywyss', 'cimw', 'fzfdkelqp', 'yvrgec', 'nsvx', 'hoqusdr'])
        self.sort_in_az(['aemoix', 'b', 'bemjtax', 'd', 'fqoudlv', 'hagrrcow', 'hro', 'jan', 'ljfflbuewp', 'mkaycp', 'nyswctr', 'sohedwds', 'tcpinx', 'tgubrdnefq', 'vkzna', 'w', 'zyxqvilsx'],
                        ['vkzna', 'b', 'ljfflbuewp', 'hro', 'hagrrcow', 'fqoudlv', 'sohedwds', 'zyxqvilsx', 'tgubrdnefq', 'jan', 'w', 'aemoix', 'mkaycp', 'd', 'tcpinx', 'nyswctr', 'bemjtax'])
        self.sort_in_az(['aulzgcze', 'bedupmduv', 'cbgpyoy', 'f', 'gwj', 'iztvh', 'lfbnojtxzf', 'lxgjajasrv', 'mihgah', 'mmvp', 'osjlflgl', 't', 'thkdq', 'x', 'y', 'yvsnqlyz', 'z'],
                        ['thkdq', 'iztvh', 'lfbnojtxzf', 'x', 'cbgpyoy', 'z', 'osjlflgl', 'mihgah', 'y', 'bedupmduv', 'f', 'mmvp', 'yvsnqlyz', 't', 'aulzgcze', 'gwj', 'lxgjajasrv'])
        self.sort_in_az(['gdnk', 'gifegelh', 'imsxbbmo', 'jbilffnp', 'o', 'obbojtj', 'oorloptce', 'q', 'sj', 'vgff', 'vts', 'waaborli', 'xttr', 'ycblbft', 'yjyypsr', 'z', 'zwjb'],
                        ['o', 'jbilffnp', 'vts', 'imsxbbmo', 'ycblbft', 'sj', 'obbojtj', 'vgff', 'q', 'zwjb', 'gdnk', 'waaborli', 'oorloptce', 'yjyypsr', 'gifegelh', 'xttr', 'z'])
        self.sort_in_az(['ampcjzkc', 'eapscsiisf', 'flbnwyacvt', 'inkklozr', 'iocmhzkg', 'n', 'pjxeiocpgg', 'tdbhcjplb', 'thwciqlkt', 'topw', 'ubiucgvtt', 'xqjaz', 'xsrpaucf', 'y', 'yq', 'ywwl', 'zgu'],
                        ['ywwl', 'tdbhcjplb', 'inkklozr', 'xqjaz', 'zgu', 'n', 'flbnwyacvt', 'ubiucgvtt', 'xsrpaucf', 'y', 'eapscsiisf', 'pjxeiocpgg', 'topw', 'iocmhzkg', 'thwciqlkt', 'ampcjzkc', 'yq'])
        self.sort_in_az(['chhxf', 'eyka', 'hlbfkptx', 'i', 'lstcji', 'magtvuj', 'mhba', 'ntpclmynzz', 'pbhjedlxn', 'pxtocqmtq', 'qk', 'qvlyr', 'rkmomw', 'tmdaaxwin', 'tnhsmyobe', 'xaxvr', 'ym', 'ytcuvfmm'],
                        ['ytcuvfmm', 'qvlyr', 'hlbfkptx', 'pbhjedlxn', 'tmdaaxwin', 'rkmomw', 'ym', 'eyka', 'xaxvr', 'chhxf', 'lstcji', 'mhba', 'i', 'tnhsmyobe', 'ntpclmynzz', 'magtvuj', 'qk', 'pxtocqmtq'])
        self.sort_in_az(['cwpimivfre', 'elqqsdz', 'genmlyol', 'hejtcgjgyq', 'mrzdakoc', 'nijfpv', 'p', 'rapgzp', 'sncnrc', 'tby', 'tjajowu', 'ujbbjqdmno', 'vztcqgku', 'wi', 'xeyelh', 'ydqajkppkv', 'yvvtahn', 'yyqfsoaw'],
                        ['mrzdakoc', 'vztcqgku', 'sncnrc', 'yvvtahn', 'cwpimivfre', 'wi', 'yyqfsoaw', 'tjajowu', 'p', 'ujbbjqdmno', 'nijfpv', 'ydqajkppkv', 'tby', 'rapgzp', 'xeyelh', 'elqqsdz', 'hejtcgjgyq', 'genmlyol'])
        self.sort_in_az(['aeyvn', 'mpmawkeyp', 'pwid', 'pxemq', 'qocinabht', 'qz', 'rfbgbhz', 'sjrocwxhp', 'skyx', 'sybtrdgg', 'tmrbysrzm', 'togsssdu', 'u', 'vsuw', 'wksczlf', 'xthbpbl', 'ysez', 'yyojkczphs'],
                        ['yyojkczphs', 'qocinabht', 'togsssdu', 'ysez', 'u', 'rfbgbhz', 'qz', 'pwid', 'mpmawkeyp', 'sjrocwxhp', 'wksczlf', 'vsuw', 'pxemq', 'aeyvn', 'skyx', 'sybtrdgg', 'xthbpbl', 'tmrbysrzm'])
        self.sort_in_az(['c', 'ddepfsdluw', 'diqfeja', 'elehhyxfx', 'fe', 'gcd', 'grmludh', 'ioiu', 'qitxnm', 'qsccyp', 'ruzldc', 'sp', 'suoaae', 'tdorfdvi', 'u', 'wczzbw', 'xylhk', 'z'],
                        ['ioiu', 'tdorfdvi', 'sp', 'diqfeja', 'gcd', 'suoaae', 'wczzbw', 'u', 'qitxnm', 'elehhyxfx', 'c', 'grmludh', 'xylhk', 'z', 'ruzldc', 'fe', 'qsccyp', 'ddepfsdluw'])
        self.sort_in_az(['cpqazxdt', 'd', 'djvayxdzls', 'f', 'fjddggtrub', 'fxtqbyggo', 'hqobssno', 'idnxzic', 'jovdmzu', 'kswkwgt', 'lecug', 'lgvkihnsg', 'rppfoc', 'tqddvixvi', 'x', 'xaddtnuh', 'xfaslw', 'zq', 'zqxngp'],
                        ['xfaslw', 'cpqazxdt', 'hqobssno', 'zqxngp', 'idnxzic', 'djvayxdzls', 'f', 'xaddtnuh', 'jovdmzu', 'x', 'kswkwgt', 'zq', 'lgvkihnsg', 'tqddvixvi', 'fxtqbyggo', 'fjddggtrub', 'rppfoc', 'd', 'lecug'])
        self.sort_in_az(['atkcnqjsqg', 'btxxb', 'cfilwzgg', 'dhrypo', 'fyym', 'ijiktjbf', 'izdhmiwe', 'ldpjl', 'mhlgcyq', 'mijgidn', 'p', 'pnsjmnbgfp', 'qqin', 'smzajugy', 'tvg', 'ueasepk', 'vgz', 'vwi', 'xvwpwdqt'],
                        ['p', 'qqin', 'btxxb', 'cfilwzgg', 'ijiktjbf', 'xvwpwdqt', 'dhrypo', 'pnsjmnbgfp', 'atkcnqjsqg', 'tvg', 'ueasepk', 'mhlgcyq', 'vgz', 'izdhmiwe', 'fyym', 'ldpjl', 'mijgidn', 'vwi', 'smzajugy'])
        self.sort_in_az(['b', 'bfflp', 'dcjvatuww', 'eqvvlaxazd', 'fizd', 'koxwk', 'nnfngj', 'ourpuwhj', 'phahnyfu', 'qslrm', 'rhivxphggh', 'ufsgt', 'vhvcv', 'vjspzyh', 'wijtzwwvei', 'wzyqpy', 'yov', 'yzihslml', 'zgzzolrt'],
                        ['vjspzyh', 'ourpuwhj', 'dcjvatuww', 'nnfngj', 'fizd', 'yov', 'koxwk', 'qslrm', 'bfflp', 'ufsgt', 'rhivxphggh', 'zgzzolrt', 'yzihslml', 'vhvcv', 'phahnyfu', 'b', 'wzyqpy', 'wijtzwwvei', 'eqvvlaxazd'])
        self.sort_in_az(['aoelmcx', 'aoib', 'dgdusy', 'ghcacuxzwh', 'hcwofn', 'hjphp', 'htfuere', 'icjsqdhdup', 'igab', 'igfaxpktql', 'kfgtsrug', 'kmsefnrh', 'lmjexjjuo', 'qaljzs', 'qqutj', 'tki', 'uarff', 'ukpff', 'wcv'],
                        ['lmjexjjuo', 'igfaxpktql', 'wcv', 'icjsqdhdup', 'aoib', 'htfuere', 'qqutj', 'aoelmcx', 'ukpff', 'kfgtsrug', 'igab', 'kmsefnrh', 'tki', 'dgdusy', 'hjphp', 'uarff', 'ghcacuxzwh', 'hcwofn', 'qaljzs'])
        self.sort_in_az(['beup', 'bfcoj', 'cz', 'czozcyaw', 'hwaviqf', 'hzxo', 'ihgknjp', 'isajjladkf', 'iybcsu', 'jgol', 'khvdpojo', 'koyb', 'lp', 'mzprf', 'rxanddw', 's', 'yzxd', 'ze', 'zqndo'],
                        ['cz', 'bfcoj', 'zqndo', 'koyb', 'czozcyaw', 'hzxo', 'jgol', 'lp', 's', 'ze', 'iybcsu', 'ihgknjp', 'rxanddw', 'khvdpojo', 'yzxd', 'hwaviqf', 'isajjladkf', 'beup', 'mzprf'])
        self.sort_in_az(['datoz', 'ekmhcbkji', 'epasxowme', 'hnkx', 'iikdgmtsvc', 'ivbhuqudsh', 'jeewmpn', 'mqybxwfpq', 'mr', 'qnswknx', 'sv', 'uxxdavlbkh', 'vdp', 'vgircqscls', 'vyngrmg', 'xhd', 'xxgyuoi', 'yi', 'zjexjcu'],
                        ['vyngrmg', 'ekmhcbkji', 'zjexjcu', 'mr', 'vdp', 'yi', 'ivbhuqudsh', 'qnswknx', 'xxgyuoi', 'epasxowme', 'iikdgmtsvc', 'uxxdavlbkh', 'hnkx', 'datoz', 'jeewmpn', 'vgircqscls', 'xhd', 'sv', 'mqybxwfpq'])
        self.sort_in_az(['awrkava', 'btva', 'dgigppnvq', 'dhcrt', 'ett', 'gynlg', 'hhmpsds', 'ipqdw', 'iqbnqlf', 'iz', 'jhtceuwgq', 'lgdoyke', 'ltlzw', 'nnspfrogg', 'oni', 'pjudprahc', 'rfhdbv', 'rwqdyu', 'zpfixa'],
                        ['iqbnqlf', 'awrkava', 'ltlzw', 'oni', 'rwqdyu', 'ipqdw', 'iz', 'nnspfrogg', 'ett', 'hhmpsds', 'dgigppnvq', 'gynlg', 'pjudprahc', 'btva', 'dhcrt', 'lgdoyke', 'rfhdbv', 'zpfixa', 'jhtceuwgq'])
        self.sort_in_az(['atowi', 'b', 'bugtrlabr', 'bysnl', 'dhpznpsvl', 'eqage', 'f', 'hdjfxwdtz', 'hpmvlwvz', 'lckxugpf', 'leedvazylf', 'mybxgir', 'nhmiomz', 'nlv', 'ol', 'slgeapii', 'slreoe', 'wwtiybiozi', 'zusuzheure'],
                        ['dhpznpsvl', 'ol', 'lckxugpf', 'nlv', 'hdjfxwdtz', 'slreoe', 'leedvazylf', 'nhmiomz', 'eqage', 'mybxgir', 'slgeapii', 'hpmvlwvz', 'bysnl', 'wwtiybiozi', 'bugtrlabr', 'f', 'b', 'zusuzheure', 'atowi'])
        self.sort_in_az(['aes', 'aih', 'bvrc', 'cdnosntqb', 'hybbm', 'ltswg', 'mjw', 'mpqvcj', 'ookyvpsgii', 'prinkdtq', 'qbuvofm', 'rdtn', 't', 'tqgh', 'ucvwzicy', 'veu', 'vkres', 'xbmjepizb', 'yexx', 'ztgbrjmhj'],
                        ['yexx', 'ltswg', 'aes', 'vkres', 'mpqvcj', 'ookyvpsgii', 'veu', 'rdtn', 'aih', 'hybbm', 'bvrc', 'ucvwzicy', 'prinkdtq', 'ztgbrjmhj', 't', 'cdnosntqb', 'qbuvofm', 'tqgh', 'mjw', 'xbmjepizb'])
        self.sort_in_az(['cyoerkc', 'djhgm', 'ezpp', 'lclzidxj', 'lwwpaygehk', 'm', 'mmhoftuk', 'orw', 'ovsmjkwda', 'pvshtlfcp', 'qiz', 'rp', 'sclig', 'tofixzn', 'und', 'vzp', 'xp', 'xsmoohlk', 'yquoipbz', 'zmhgggjb'],
                        ['xp', 'ovsmjkwda', 'tofixzn', 'ezpp', 'pvshtlfcp', 'mmhoftuk', 'djhgm', 'vzp', 'cyoerkc', 'qiz', 'orw', 'yquoipbz', 'und', 'rp', 'm', 'lclzidxj', 'lwwpaygehk', 'sclig', 'xsmoohlk', 'zmhgggjb'])
        self.sort_in_az(['avr', 'bnbddhkwoa', 'coorbh', 'diwwh', 'dsit', 'gfws', 'gvopbopc', 'gwype', 'hvt', 'ivzjoc', 'jkvcwpo', 'kg', 'kkhdtx', 'ksddx', 'nfezywm', 'ogdwu', 'p', 'qlwxkpib', 'rbgtqmv', 'wskvcpcrz'],
                        ['nfezywm', 'ksddx', 'coorbh', 'dsit', 'gfws', 'hvt', 'p', 'qlwxkpib', 'gvopbopc', 'avr', 'wskvcpcrz', 'bnbddhkwoa', 'kg', 'kkhdtx', 'diwwh', 'gwype', 'ivzjoc', 'ogdwu', 'rbgtqmv', 'jkvcwpo'])


    def sort_in_az(self, expected, to_sort):
        self.assertEqual(expected, self.do_sort(self.az, to_sort))

    def sort_in_actg(self, expected, to_sort):
        self.assertEqual(expected, self.do_sort(self.acgt, to_sort))

    def do_sort(self, alphabet, array):
        return sorted(array)


if __name__ == '__main__':
    unittest.main()