package resources.specification;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

public class SimplePageRequest implements Pageable {

	private Pageable inner;
	@QueryParam("page")
	@DefaultValue("0")
	private Integer page;
	@QueryParam("pageSize")
	@DefaultValue("20")
	private Integer pageSize;
	@QueryParam("sort")
	@DefaultValue("id")
	private String sortParam;
	@QueryParam("dir")
	@DefaultValue("ASC")
	private String dir;

	public SimplePageRequest() {

	}

	public SimplePageRequest(Integer page, Integer pageSize, String sortParam,
			String dir) {
		super();
		this.page = page;
		this.pageSize = pageSize;
		this.sortParam = sortParam;
		this.dir = dir;
	}

	@Override
	public int getPageNumber() {
		return getInner().getPageNumber();
	}

	@Override
	public int getPageSize() {
		return getInner().getPageSize();
	}

	@Override
	public int getOffset() {
		return getInner().getOffset();
	}

	@Override
	public Sort getSort() {
		return getInner().getSort();
	}

	@Override
	public Pageable next() {
		return getInner().next();
	}

	@Override
	public Pageable previousOrFirst() {
		return getInner().previousOrFirst();
	}

	@Override
	public Pageable first() {
		return getInner().first();
	}

	@Override
	public boolean hasPrevious() {
		return getInner().hasPrevious();
	}

	private Pageable getInner() {
		if (inner == null) {
			Sort sort = new Sort(Direction.valueOf(dir), sortParam);
			inner = new PageRequest(page, pageSize, sort);
		}
		return inner;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public String getSortParam() {
		return sortParam;
	}

	public void setSortParam(String sortParam) {
		this.sortParam = sortParam;
	}

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public void setInner(Pageable inner) {
		this.inner = inner;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
