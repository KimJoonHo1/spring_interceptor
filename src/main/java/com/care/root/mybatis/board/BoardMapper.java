package com.care.root.mybatis.board;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.care.root.board.dto.BoardDTO;
import com.care.root.board.dto.BoardRepDTO;

public interface BoardMapper {
	// 파라미터가 두개 이상일시 @Param 어노테이션을 사용해야함
	public List<BoardDTO> selectAllBoardList(@Param("s") int start, @Param("e") int end);
	public int writeSave(BoardDTO dto);
	public BoardDTO contentView(int writeNo);
	public void upHit(int writeNo);
	public int delete(int writeNo);
	public int modify(BoardDTO dto);
	public int addReply(BoardRepDTO dto);
	public List<BoardRepDTO> getRepList(int write_group);
	public int selectBoardCount();
}
