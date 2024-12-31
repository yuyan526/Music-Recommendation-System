def file_remove_same(input_file_name, output_file_name):
    """
    小文件去重
    :param input_file_name: 输入文件夹文件名
    :param output_file_name: 输出文件夹文件名
    :return:
    """
    with open(input_file_name, 'r', encoding='utf-8') as input, open(output_file_name, 'a', encoding='utf-8') as output:
        input_lines = []
        for line in input:
            if line not in input_lines:
                input_lines.append(line)
                output.write(line)
                output.flush()
        input.close()
        output.close()

# 去重
file_remove_same('dataset/user_info_init.txt', 'dataset/user_info.txt')
